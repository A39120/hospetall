package pt.hospetall.web.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.hospetall.web.error.exceptions.PetNotFoundException;
import pt.hospetall.web.model.WaitingListConsultation;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.repository.IWaitingListConsultationRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.BiFunction;

@Service
public class WaitingListService {

    private final IWaitingListConsultationRepository waitingListConsultationRepository;

    private HashMap<Integer, PriorityQueue<WaitingListConsultation>> hashMapWLC;
    private PriorityQueue<WaitingListConsultation> pQWLC;
    private TreeSet<WaitingListConsultation> treeSetWLC;

    private final Comparator cmp = Comparator.comparing(WaitingListConsultation::getPatientPriority);




    @Autowired
    public WaitingListService(IWaitingListConsultationRepository waitingListConsultationRepository){

        this.waitingListConsultationRepository = waitingListConsultationRepository;
    }


    @PostConstruct
    public void initIt() throws Exception {
        hashMapWLC  = new HashMap<>();
        pQWLC = new PriorityQueue<>(10, cmp);
        treeSetWLC = new TreeSet<>(cmp);

        Iterable<WaitingListConsultation> wlc_ite = waitingListConsultationRepository.findAll();
        wlc_ite.forEach((wlc) -> insertInQueue(wlc));
    }



    public void addToWLConsultation(WaitingListConsultation wlc) {
        if(waitingListConsultationRepository.findByPet(wlc.getPet()).isPresent())
                throw new PetNotFoundException();  //PetAlreadyInserted

       insertInQueue(wlc);

        waitingListConsultationRepository.save(wlc);

    }


    private void insertInQueue(WaitingListConsultation wlc){
        if(wlc.getVet() != null) {
            Integer id_vet = wlc.getVet().getId();
            if (hashMapWLC.get(id_vet) == null) {
                hashMapWLC.put(id_vet, new PriorityQueue<>(10,
                        Comparator.comparing(WaitingListConsultation::getPatientPriority)));
            }
            hashMapWLC.get(id_vet).add(wlc);
        }
        else{pQWLC.add(wlc);}

        treeSetWLC.add(wlc);
    }



    public WaitingListConsultation pollFromWLConsultation(int vet_id){
        WaitingListConsultation wlc = null;

        PriorityQueue<WaitingListConsultation> priorityQueue_vet = hashMapWLC.get(vet_id);
        if(priorityQueue_vet != null && !priorityQueue_vet.isEmpty()) {
            if(!pQWLC.isEmpty())
                wlc = priorityQueue_vet.peek().getPatientPriority() >= pQWLC.peek().getPatientPriority() ? priorityQueue_vet.poll() : pQWLC.poll();
            else wlc = priorityQueue_vet.poll();
        }
        else if(!pQWLC.isEmpty())
            wlc = pQWLC.poll();

        if(wlc != null) {
            waitingListConsultationRepository.delete(wlc);
            treeSetWLC.remove(wlc);
        }
        return wlc;
    }


    /*
    public WaitingListConsultation changeElemWLConsultation(WaitingListConsultation wlc){
        Optional<WaitingListConsultation> wlc = Optional.ofNullable((WaitingListConsultation) waitingListConsultationQueue.poll());
        if(wlc.isPresent()) waitingListConsultationRepository.delete(wlc);
        return wlc.get();
    }*/

    public WaitingListConsultation peekFromWLConsultation(int vet_id){
        WaitingListConsultation wlc = null;

        PriorityQueue<WaitingListConsultation> priorityQueue_vet = hashMapWLC.get(vet_id);
        if(priorityQueue_vet != null && !priorityQueue_vet.isEmpty()) {
            if(!pQWLC.isEmpty())
                wlc = priorityQueue_vet.peek().getPatientPriority() >= pQWLC.peek().getPatientPriority() ? priorityQueue_vet.peek() : pQWLC.peek();
            else wlc = priorityQueue_vet.peek();
        }
        else if(!pQWLC.isEmpty())
            wlc = pQWLC.peek();

        return wlc;
    }


    public List<WaitingListConsultation> getSorted(){
        return new ArrayList<>(treeSetWLC);
    }

}
