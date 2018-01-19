
import models.Team;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

//HERE WE GO

public class App{
    public static void main(String[] args) {
        staticFileLocation("/public");

//        //dummy teams for use in testing
//        Team sleepHabits = new Team("Better Sleep Habits", "we are setting out to make an app that will help the user easily track their sleep habits and help them to make improvements with scientifically backed suggestions");
//        sleepHabits.addMember("Aaron Ross");
//        sleepHabits.addMember("Most Students");
//        sleepHabits.addMember("Another Human");
//        sleepHabits.addMember("CoolGuy McFriendyface");
//
//        Team charityRater = new Team("Charity Tracker", "our goal is to create an app that rates charities based on their impact on the world so that the end userr can choose wisely how to spend their limited resources");
//        charityRater.addMember("Human 1");
//        charityRater.addMember("quixote");
//        charityRater.addMember("g00n$13");
//
//
//    //get: landing page per guidelines will show all teams and descriptions
//        get("/", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            ArrayList<Team> teams = Team.getAllTeams();
//            model.put("teams", teams);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: show about
//        get("/about", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "about.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: show form to add a new team
//        get("/teams/new", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "team-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //post: process new team form
//        post("/teams/new", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            String newTeamName = request.queryParams("name");
//            String newTeamDescription = request.queryParams("description");
//            Team newTeam = new Team(newTeamName, newTeamDescription);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: show a single team and form to add members
//        get("/teams/:id", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTeamToFind = Integer.parseInt(request.params("id"));
//            Team foundTeam = Team.findTeamById(idOfTeamToFind);
//            model.put("team", foundTeam);
//            return new ModelAndView(model, "team-detail.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //post: process form to add members
//        post("/teams/:id/member/new", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            String newMember = request.queryParams("member-name");
//            int idOfTeamToAddMembersTo = Integer.parseInt(request.params("id"));
//            Team teamToAddMemberTo = Team.findTeamById(idOfTeamToAddMembersTo);
//            teamToAddMemberTo.addMember(newMember);
//            return new ModelAndView(model, "member-success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: show form to update team name/description
//        get("/teams/:id/update", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTeamToUpdate = Integer.parseInt(request.params("id"));
//            Team updateTeam = Team.findTeamById(idOfTeamToUpdate);
//            model.put("updateTeam", updateTeam);
//            return new ModelAndView(model, "team-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //post: process update form
//        post("/teams/:id/update", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            String updatedName = request.queryParams("name");
//            String updatedDescription = request.queryParams("description");
//            int idOfTeamToUpdate = Integer.parseInt(request.params("id"));
//            Team updateTeam = Team.findTeamById(idOfTeamToUpdate);
//            updateTeam.updateName(updatedName);
//            updateTeam.updateDescription((updatedDescription));
//            return new ModelAndView(model,"success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//
//
//
    }
}