package pt.hospetall.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.hospetall.web.error.exceptions.ScheduleConflictException;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.model.shift.Shift;
import pt.hospetall.web.model.shift.VeterinarianShift;
import pt.hospetall.web.repository.INurseShiftRepository;
import pt.hospetall.web.repository.IShiftRepository;
import pt.hospetall.web.repository.IVeterinarianShiftRepository;
import pt.hospetall.web.util.CalendarUtil;

import java.util.Calendar;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class ShiftService {

	private final INurseShiftRepository nurseShiftRepository;

	private final IVeterinarianShiftRepository veterinarianShiftRepository;

	@Autowired
	public ShiftService(INurseShiftRepository nurseShiftRepository, IVeterinarianShiftRepository veterinarianShiftRepository) {
		this.nurseShiftRepository = nurseShiftRepository;
		this.veterinarianShiftRepository = veterinarianShiftRepository;
	}


	/**
	 * Refractors all shifts.
	 * @param newShift, the new shift to add
	 * @param existing, the previous shifts
	 * @param repository, nurse repository
	 * @return the shift saved in database.
	 */
	public <U extends IShiftRepository<T>, T extends Shift> T addShift( T newShift, Set<T> existing, U repository)
			throws ScheduleConflictException{

		for(T shift : existing){
			if(newShift.inside(shift) && shift.isWeekly())
				throw new ScheduleConflictException("Can't schedule a shift for inside another shift");

			//Older smaller shift will be overriden
			if(shift.inside(newShift) && newShift.isWeekly())
				repository.delete(shift);
			else{
				if(newShift.intersect(shift)) {
					repository.delete(shift);
					if(newShift.isWeekly() == shift.isWeekly() || !newShift.isWeekly()){
						newShift.intersect(shift);
					} else if(!shift.isWeekly()){
						shift.intersect(newShift);
						repository.save(shift);
					}
				}
			}
		}
		return repository.save(newShift);
	}

	/**
	 * @return all shifts depending on the repository used.
	 */
	private <U extends Shift, T extends IShiftRepository<U>> PriorityQueue<U> getShiftsOrderedByStart(T repo){
		PriorityQueue<U> queue = new PriorityQueue<>();
		repo.findAll().forEach(queue::add);
		return queue;
	}


	/**
	 * @return priority queue from a set
	 */
	private <T extends Shift> PriorityQueue<T> convertSetToPriorityQueue(Set<T> set){
		return set.stream()
				.collect( PriorityQueue::new, PriorityQueue::add, PriorityQueue::addAll );
	}

	/**
	 * @return all shifts from a determined start and end
	 */
	private <U extends Shift, T extends IShiftRepository<U>> PriorityQueue<U> getAllShiftsBetween(long start,
																								  long end,
																								  T repo,
																								  Function<U, PriorityQueue<U>> mapper){
		return repo
				.findAllByStartPeriodAfterOrWeeklyIsTrue(start)
				.stream()
				.filter(s -> s.isWeekly() || s.calculateEnd() < end)
				.map(mapper)
				.collect( PriorityQueue::new, PriorityQueue::addAll, PriorityQueue::addAll );
	}

	/**
	 * @return all veterinarian shifts, non filtered
	 */
	public PriorityQueue<VeterinarianShift> getAllVeterinarianShifts(){
		return getShiftsOrderedByStart(veterinarianShiftRepository);
	}

	/**
	 * @return all nurses shifts
	 */
	public PriorityQueue<NurseShift> getAllNursesShifts(){
		return getShiftsOrderedByStart(nurseShiftRepository);
	}

	/**
	 * @return all nurses shifts filtered by start and end
	 */
	public PriorityQueue<NurseShift> getAllNursesShiftsBetween(long start, long end){
		return getAllShiftsBetween(start, end, nurseShiftRepository, (shift) -> nurseShiftWeeklyToNonWeekly(shift, start, end));
	}

	/**
	 * @return all nurses shifts filtered by start and end
	 */
	public PriorityQueue<VeterinarianShift> getAllVeterinariansShiftsBetween(long start, long end){
		return getAllShiftsBetween(start, end, veterinarianShiftRepository, (shift) -> veterinarianShiftWeeklyToNonWeekly(shift, start, end));
	}

	/**
	 * @return one nurse shifts ordered by start date
	 */
	public PriorityQueue<NurseShift> getNurseShift(Nurse nurse){
		return convertSetToPriorityQueue(nurse.getShifts());
	}

	/**
	 * @return one nurse shifts ordered by start date
	 */
	public PriorityQueue<VeterinarianShift> getVeterinarianShift(Veterinarian veterinarian){
		return convertSetToPriorityQueue(veterinarian.getShifts());
	}

	public <T extends Shift> PriorityQueue<T> getShiftFromASet(Set<T> set, Function<T, PriorityQueue<T>> mapper){
		return set
				.stream()
				.map(mapper)
				.collect(PriorityQueue::new, PriorityQueue::addAll, PriorityQueue::addAll);
	}

	/**
	 * @return one nurse shifts ordered by start date, filtered by start and end
	 */
	public PriorityQueue<NurseShift> getNurseShiftBetween(Nurse nurse, long start, long end){
		return getShiftFromASet(nurse.getShifts(), (s) -> nurseShiftWeeklyToNonWeekly(s, start, end));
	}

	/**
	 * @return one veterinarian shifts ordered by start date, filtered by start and end date
	 */
	public PriorityQueue<VeterinarianShift> getVeterinarianShiftBetween(Veterinarian veterinarian, long start, long end){
		return getShiftFromASet(veterinarian.getShifts(), (s) -> veterinarianShiftWeeklyToNonWeekly(s, start, end));
	}

	public <T extends Shift, U extends IShiftRepository<T>> T save(T shift, U repo, Iterable<T> existing) throws ScheduleConflictException{
		for(Shift current : existing)
			if(current.intersect(shift) || current.inside(shift) || shift.inside(current))
				throw new ScheduleConflictException("Shifts intersect");

		return repo.save(shift);
	}

	public <T extends Shift, U extends IShiftRepository<T>> T update(T shift, U repo, Iterable<T> existing)
			throws ScheduleConflictException {

		for(Shift current : existing)
			if((current.intersect(shift) || current.inside(shift) || shift.inside(current)) && current.getId() != shift.getId())
				throw new ScheduleConflictException("Shifts intersect");

		return repo.save(shift);
	}

	private  <T extends Shift> PriorityQueue<T> weeklyToNonWeekly(T shift,
																long start,
																long end,
																BiFunction<Long, Integer, T> builder){
		PriorityQueue<T> list = new PriorityQueue<>();
		if(shift.isWeekly()){
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.setTimeInMillis(start);

			Calendar current = Calendar.getInstance();
			current.setTimeInMillis(shift.getStartPeriod());

			int currentDayOfWeek = current.get(Calendar.DAY_OF_WEEK);

			Calendar calendarEnd = Calendar.getInstance();
			calendarEnd.setTimeInMillis(end);

			current = CalendarUtil.setDay(calendarStart, current);
			while(current.get(Calendar.DAY_OF_WEEK) != currentDayOfWeek && current.before(calendarEnd))
				current.add(Calendar.DAY_OF_WEEK, 1);

			for (; current.before(calendarEnd) ; current.add(Calendar.WEEK_OF_YEAR, 1))
				list.add(builder.apply(current.getTimeInMillis(), shift.getMinutes()));


			/*while(current.before(calendarEnd)){
				list.add(builder.apply(current.getTimeInMillis(), shift.getMinutes()));
				current.add(Calendar.WEEK_OF_YEAR, 1);
			}*/
		} else if(shift.getStartPeriod() > start && shift.calculateEnd() < end)
			list.add(shift);
		return list;
	}

	private PriorityQueue<NurseShift> nurseShiftWeeklyToNonWeekly(NurseShift shift, long start, long end){
		return weeklyToNonWeekly(shift, start, end, (strt, min) -> {
			NurseShift newShift = new NurseShift();
			newShift.setNurse(shift.getNurse());
			newShift.setWeekly(false);
			newShift.setStartPeriod(strt);
			newShift.setMinutes(min);
			return newShift;
		});
	}

	private PriorityQueue<VeterinarianShift> veterinarianShiftWeeklyToNonWeekly(VeterinarianShift shift, long start, long end){
		return weeklyToNonWeekly(shift, start, end, (strt, min) -> {
			VeterinarianShift newShift = new VeterinarianShift();
			newShift.setVeterinarian(shift.getVeterinarian());
			newShift.setWeekly(false);
			newShift.setStartPeriod(strt);
			newShift.setMinutes(min);
			return newShift;
		});
	}


}
