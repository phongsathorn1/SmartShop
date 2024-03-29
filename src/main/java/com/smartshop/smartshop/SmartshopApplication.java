package com.smartshop.smartshop;

import com.smartshop.smartshop.controller.SmartshopFactory;
import com.smartshop.smartshop.model.Watch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class SmartshopApplication {

    @Autowired
    private SmartshopFactory smartshopFactory = SmartshopFactory.getInstance();

    @RequestMapping(value = "/product/all", method = RequestMethod.GET)
    public List<Watch> index(@RequestParam(value = "brand", defaultValue = "") String brand){
        if(brand.isEmpty()) {
            return smartshopFactory.getWatches();
        }else{
            return smartshopFactory.getByBrand(brand);
        }
    }

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public Map<String, Object> add(@Valid @RequestBody Watch watch){
        Watch newWatch = smartshopFactory.createWatch();
        newWatch.setProductName(watch.getProductName());
        newWatch.setBrand(watch.getBrand());
        newWatch.setContent(watch.getContent());
        newWatch.setImageURL(watch.getImageURL());
        newWatch.setPrice(watch.getPrice());

        smartshopFactory.save(newWatch);

        HashMap<String, Object> response = new HashMap<>();
        response.put("Success", true);
        response.put("Payload", newWatch);
        return response;
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public Map<String, Boolean> remove(@PathVariable("id") long id){
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("Success", smartshopFactory.remove(id));
        return response;
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public Optional<Watch> get(@PathVariable("id") long id){
        return smartshopFactory.getWatch(id);
    }

    public static void main(String[] args){
//        Watch watch1 = smartshopFactory.createWatch();
//        watch1.setProductName("Mens Hugo Boss Watch 1513394");
//        watch1.setBrand("Hugo Boss");
//        watch1.setContent("Hugo Boss 1513394 is an amazing and very impressive Gents watch. Material of the case is " +
//                        "Stainless Steel while the dial colour is White. In regards to the water resistance, the watch" +
//                        " has got a resistancy up to 50 metres. It means it can be submerged in water for periods, so " +
//                        "can be used for swimming and fishing. It is not reccomended for high impact water sports. We " +
//                        "ship it with an original box and a guarantee from the manufacturer.");
//        watch1.setImageURL("https://d1rkccsb0jf1bk.cloudfront.net/products/100029622/main/large/1513394.jpg");
//        watch1.setPrice(400.00);
////        System.out.println(watch1);
//        smartshopFactory.save(watch1);
//
//        Watch watch2 = smartshopFactory.createWatch();
//        watch2.setProductName("Mens Casio Edifice Bluetooth Triple Connect Toro Rosso Special Edition Alarm " +
//                        "Chronograph Watch EQB-800TR-1AER");
//        watch2.setBrand("Casio");
//        watch2.setContent("The Casio Edifice Bluetooth Triple Connect Toro Rosso Special Edition EQB-800TR-1AER is an " +
//                        "amazing and attractive Gents watch. The latest iteration of Casio's Edifice family " +
//                        "includes their new Triple Connect system, linking to radio time signals, GPS and Bluetooth " +
//                        "to ensure your watch is constantly accurate, and display the time in over 300 city time zones " +
//                        "around the world. ");
//        watch2.setImageURL("https://d1rkccsb0jf1bk.cloudfront.net/products/100014542/main/large/eqb-800tr-1aer-1500993057-2485.jpg");
//        watch2.setPrice(560.00);
//        smartshopFactory.save(watch2);

        SpringApplication.run(SmartshopApplication.class, args);
    }
}
