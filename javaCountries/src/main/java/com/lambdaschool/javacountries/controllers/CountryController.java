package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    @Autowired
    private CountriesRepository countryrepo;

    private List<Country> findCountries(List<Country> myList, CheckCountry tester)
    {
        List<Country> tempList = new ArrayList<>();

        for (Country c : myList)
        {
            if (tester.test(c))
            {
                tempList.add(c);
            }
        }

        return tempList;
    }

    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries()
    {
        List<Country> myList = new ArrayList<>();

        countryrepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        for (Country c : myList)
        {
            System.out.println(c);
        }

        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/u
    @GetMapping(value="/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllByLetter(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();

        countryrepo.findAll().iterator().forEachRemaining(myList::add);

        List<Country> rtnList = findCountries(myList, c -> c.getName().charAt(0) == letter);

        rtnList.sort((c1,c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> displayTotal()
    {
        List<Country> myList = new ArrayList<>();

        countryrepo.findAll().iterator().forEachRemaining(myList::add);

        long total = 0;
        for (Country c : myList)
        {
            total += c.getPopulation();
        }

        System.out.println("Population total " + total);
        return new ResponseEntity<>( HttpStatus.OK );
    }
    // http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> displayMin()
    {
        //creating empty array myList =[]
        List<Country> myList = new ArrayList<>();

        //Adding all countries to myList
        countryrepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> c1.getPopulation() > c2.getPopulation() ? 1 : -1);

        return new ResponseEntity<>( myList.get(0),HttpStatus.OK );
    }

    // http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> displayMax()
    {
        //creating empty array myList =[]
        List<Country> myList = new ArrayList<>();
        //Adding all countries to myList
        countryrepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> c1.getPopulation() < c2.getPopulation() ? 1 : -1);

        return new ResponseEntity<>( myList.get(0),HttpStatus.OK );

    }
    // http://localhost:2019/population/median
    @GetMapping(value = "/population/median", produces = {"application/json"})
    public ResponseEntity<?> displayMedian()
    {
        //creating empty array myList =[]
        List<Country> myList = new ArrayList<>();
        //Adding all countries to myList
        countryrepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> c1.getPopulation() > c2.getPopulation() ? 1 : -1);

        return new ResponseEntity<>( myList.get(myList.size()/2 + 1), HttpStatus.OK );

    }


}
