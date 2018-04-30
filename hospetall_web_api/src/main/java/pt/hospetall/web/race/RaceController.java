package pt.hospetall.web.race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/race")
public class RaceController {

    private final RaceRepository raceRepository;

    @Autowired
    public RaceController(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Iterable<Race> getRaces(
            @RequestParam(value = "name", required = false) String name
    ){
        if(name != null)
            return raceRepository.findRacesByNameContaining(name);

        return raceRepository.findAll();
    }



}
