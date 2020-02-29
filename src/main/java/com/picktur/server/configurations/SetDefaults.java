package com.picktur.server.configurations;

import com.arangodb.springframework.core.ArangoOperations;
import com.picktur.server.entities.Category;
import com.picktur.server.entities.Tag;
import com.picktur.server.entities.user_registration.Role;
import com.picktur.server.entities.user_registration.RoleName;
import com.picktur.server.repositories.documents.CategoryRepo;
import com.picktur.server.repositories.documents.RoleRepo;
import com.picktur.server.repositories.documents.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class SetDefaults {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    ArangoOperations operations;

    @Autowired
    TagRepo tagRepo;

    @Autowired
    CategoryRepo catRepo;

    @PostConstruct
    private void init(){
        //operations.dropDatabase();

        // Add ROLEs
        if (!roleRepo.findByName(RoleName.ROLE_ADMIN).isPresent()){
            Role roleAdmin = new Role();
            roleAdmin.setName(RoleName.ROLE_ADMIN);
            roleRepo.save(roleAdmin);
        }

        if (!roleRepo.findByName(RoleName.ROLE_USER).isPresent()){
            Role roleUser = new Role();
            roleUser.setName(RoleName.ROLE_USER);
            roleRepo.save(roleUser);
        }

        // Add Tags
        if (tagRepo.count() == 0){
            Set<String> tagList = new HashSet<>();
            List<Tag> tags = new ArrayList<>();
            String patternString = "[^,\\s][^\\,]*[^,\\s]*";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(rawTags);
            while (matcher.find()) {
                tagList.add(matcher.group());
            }
            tagList.forEach(t -> {
                Tag tag = new Tag();
                tag.setValue(t);
                tags.add(tag);
            }  );
            tagRepo.saveAll(tags);
        }

        // Add Categories
        if (catRepo.count() == 0){
            List<String> catList = new ArrayList<>();
            List<Category> cats = new ArrayList<>();
            String patternString = "[^,\\s][^\\,]*[^,\\s]*";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(rawCategoriess);
            while (matcher.find()) {
                catList.add(matcher.group());
            }
            catList.forEach(t -> {
                Category cat = new Category();
                cat.setValue(t);
                cats.add(cat);
            }  );
            catRepo.saveAll(cats);
        }

    }

    private String rawTags = "woman, girl, female, person, people, caucasian, black, oriental , pretty, beauty, beautiful, blonde, adult, young, portrait, man, boy, male, guy, person, people, caucasian, oriental, african, white, teenager, baby, child, kid, sweet, little, old, elder , senior, old, pension, elder, older, ancient, elderly, portrait, grandpa, grandfather, age, architecture, travel, building, outdoors, city, tourism, landmark , palace, traditional, street, urban, nature, countryside, country, landscaped, land, grass, field, meadow, lawn, horizon, outdoor, green, ambient, sky, panorama, mountain, water, lake, snow, destination, desolate, travel, traveling, trek, trekking, visiting, landmark, park, sea, ocean, water, seaside, beach, sand, outdoor, sky, wave, nature, summer, wave, leisure, sun, seashare, holiday , lifestyle, ocean, beam, travel, blue, bright, coast, distance, horizon, light, sunshine, view, computer, laptop, internet, technology, pc, notebook, web, net, communication, modern, homework, computerhardware, hardware, studying, work, working, sadness, sad, unhappy, despair, preoccupation, concern, cry, sorrowful, sorry, feeling, expression, happy, happiness, smile, smiling, joy, joyfull, angry, scream, screaming, emotions, frustration, love, fatigue, student, study, school, education, learn, classmate, classroom, schoolchild, university, backpack, smart phone, phone, smartphone, mobile, telephone, technology, communication, app, application, message, sport, fit, fitness, activity, strong, effort, fatigue, movement, training, healthy, muscle, muscle, work out, sportsmen, run, running, weight, exercise, yoga, agility, gymnastic, relax, sportsground, stadio, sportswear, football, tennis, house, indoor, confortable, comfort, apartment, at home, interiordesign, design, home, homedocor, designer, living, style, interiors, art, furniture, decor, living room, kitchen, bedroom, bath, food,healthy, meal, colourful, delicious, details, ingredient, tasty, sald, sweet, yummy, dish, rustic, lunch, restaurant, dinner, spice, vegetables, meet, fruit, pasta, pizza, bread, preparing, cooking, kitchen, eat, eating, drink, drinking, cocktails, coca-cola, refreshment, freshness, delicious, bottle, closeup, details, coffe, cappuccino, drink espresso, mug, beige, freshness, ceramic, paper, morning, afternoon, weather, rain, raining, cloudy, cloud, storm, wind, windy, snow, snowing, glasses, sunglasses, people , person, work, laboratory, art, manual, shoop, craftsman, artisian, sell, work, job, beard, hat, white, dance, ballet, ballerina, apparel, athlete, choreographer, concentration, health, art, dancing, pose, posing, practice, music, ballet, posing, sport, fashion, fashionable, style, look, stylish, trendy, coat, skirt, dress, pants, jeans, casual, elegant, alternative, bar, evening, drink, smoke, elegant, night, lights, drinking, beer, cocktails, friends, talk, talking, music, free time, music, musician, performance, guitar, singer, drum, piano, voice, sing, singing, concert, play, pub, art, artistic, sounds, dance, dancing,alcohol, drink, drinking, dancing, dance, club, clubbing, party, celebrate, city life, colourful, enjoy, enjoying, disco, discotheque, friend, friendship, happy, indoor, night, night life, trendy, urban, youth, youth culture, love, family, mom, dad, mother, father, daddy, mommy, daughter, son, sister, brother, adult, child, kids, teenager, happy, group, marriage, together, real, relationship, parent, family-portrait, business, businessman, indoor, office, table, desk, modern,, success, elegant, jacket, co-workers, work, working, job, conference, connecting, businesswoman, businesspeople, discussion, leadership, meeting, millennials, open space, presentation, strategy, success, training, work, work-space, season, winter, snow, outside, cold, white, weather, summer, sunny, sun, warm, hot, bluesky, happy, happiness, life, spring, windy, wind, autumn, leaves, cloud, cloudy, fog, foggy, rain, raining, storm, snowing, shopping, bag, retail, shop, sale, stock, urban, city, clothes, buy, paper bag, fashion, urban, walking, indoors, relaxation, active, activity, asana, athletic, beauty, body, concentrated, concentration, copy space, endurance, energy, exercise, fit, fitness, flexibility, flexible, gym, gymnastics, harmony, health, healthy, instructor, meditating, meditation, morning, peaceful, pose, posing, posture, practice, practicing, spirit, spirituality, sport, sportswear, sporty, strength, stretching, studio, therapeutic, training, wellbeing, wellness, workout, yoga, yogi, snow, ice, landscape, mountain, nature, mountain, peak, outdoors, blue sky, clear sky, europe, goner, holiday, international, landmark, light, magical, matterhorn, morning, mountains, travel, traveling, culture, place, beautiful, country, desert, landscape, outdoor, sand, arabic, destination, walking, backpack, downhill, hike, hiking, scenic, walking, summer, riding, animals, adventure, fisherman, senior, man, portrait, water adventure, agriculture, asia, authentic, back to basics, balance, beautiful scenery, boat, burma, burmese, culture, dugout boat, early morning, environment, ethnic, famous place, first light, fisher, fishing, inlet lake, lake, male, morning, myanmar, nature, old-fashioned, outdoor person, real people, rural scene, sea, shan, state, skill, south east, asia, sunrise, tradition, traditional clothing, traditional culture, transportation, travel destinations, village";
    private String rawCategoriess = "Abstract, Animals/Wildlife, Arts, Backgrounds/Textures, Beauty/Fashion, Buildings/Landmarks, Business/Finance, Celebrities, Education, Food and drink, Healthcare/Medical, Holidays, Industrial, Interiors, Miscellaneous, Nature, Objects, Parks/Outdoor, People, Religion, Science, Signs/Symbols, Sports/Recreation, Technology, Transportation, Vintage";
}
