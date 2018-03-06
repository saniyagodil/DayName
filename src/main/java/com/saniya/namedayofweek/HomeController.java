package com.saniya.namedayofweek;


import com.saniya.namedayofweek.Repositories.BirthInfoRepository;
import com.saniya.namedayofweek.Repositories.RoleRepository;
import com.saniya.namedayofweek.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BirthInfoRepository birthInfoRepository;

    @RequestMapping("/")
    public String getHome(){
        return "Home";
    }

    @GetMapping("/newuser")
    public String getUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "UserRegistration";
    }

    @PostMapping("/newuser")
    public String processUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "UserRegistration";
        }
        user.addRole(roleRepository.findByRoleName("USER"));
        userRepository.save(user);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(Model model){
        return "Login";
    }


    @GetMapping("/getname")
    public String getDate(Model model){
        String date = new String();
        model.addAttribute("date", date); ///check the object type
        return "Form";
    }

    @PostMapping("/getname")
    public String processDate(@Valid @ModelAttribute("date") String date, Model model, Authentication auth){
        User thisUser = userRepository.findByUsername(auth.getName());
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int a = Integer.parseInt(date.substring(0,2));
        int b = Integer.parseInt(date.substring(2,4));
        int c = Integer.parseInt(date.substring(4,8));
        LocalDate userDate= LocalDate.of(c, b, a);
        if(userDate.isAfter(LocalDate.now())){
            return "Form";
        }
        String day = userDate.getDayOfWeek().name();  ///Get the full word for day of the week
        int x = getIndex(day); //get index of day of the week, Monday is 0, Sunday is 6


        BirthInfo newInfo = new BirthInfo(userDate, day, getMaleName(x), getFemaleName(x), getChar(x));
        birthInfoRepository.save(newInfo);
        thisUser.addBirthInfo(newInfo); ///adding birth info to user
        userRepository.save(thisUser);
        model.addAttribute("day", day);
        model.addAttribute("Malename", getMaleName(x));
        model.addAttribute("Femalename", getFemaleName(x));
        model.addAttribute("characteristics", getChar(x));
        return "Results";
    }

    @RequestMapping("/name")
    public String showName(Authentication auth, Model model){
        User thisUser = userRepository.findByUsername(auth.getName());
        BirthInfo birthInfo = thisUser.getRecentBirthInfo();
        model.addAttribute("day", birthInfo.getDay());
        model.addAttribute("Malename", birthInfo.getMalename());
        model.addAttribute("Femalename", birthInfo.getFemalename());
        model.addAttribute("characteristics", birthInfo.getCharacteristics());
        return "Results";
    }






    public int getIndex(String day){
       String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        int x = Arrays.asList(days).indexOf(day);
        return x;
    }

    public String getMaleName(int x){
        String[] mnames = {"Kojo", "Kwabena", "Kweku", "Yaw", "Kofi", "Kwame", "Kwesi"};
        return mnames[x];
    }

    public String getFemaleName(int x){
        String[] fnames = {"Adjoa", "Abena", "Akua", "Yaa", " Afua", "Ama", "Akosua"};
        return fnames[x];
    }

    public String getChar(int x){
        String[] charac = {"Calm, peaceful",  "Warrior, fierce, compassionate", "Advocate, controlling", "Confrontational, aggressive", "Adventurous, creative, innovative", "The ancient wise one", "Born leader, guide, protector"};
        return charac[x];
    }


}
