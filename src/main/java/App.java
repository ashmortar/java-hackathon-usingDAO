
import dao.Sql2oMemberDao;
import dao.Sql2oTeamDao;
import models.Member;
import models.Team;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.sql.rowset.serial.SerialStruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;


public class App{
    public static void main(String[] args) {
        staticFileLocation("/public");

        String connectionString = "jdbc:h2:~/hackathon.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        Sql2oMemberDao memberDao = new Sql2oMemberDao(sql2o);
        Sql2oTeamDao teamDao = new Sql2oTeamDao(sql2o);


        //=============================================================================delete routes

        //get: delete a member
        get("/teams/:teamId/members/:memberId/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            int teamId = Integer.parseInt(request.params("teamId"));
            Team team = teamDao.findById(teamId);
            model.put("team", team);

            int memberId = Integer.parseInt(request.params("memberId"));
            Member member = memberDao.findById(memberId);
            model.put("member", member);

            memberDao.deleteById(memberId);

            return new ModelAndView(model, "member-success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete all members of a team
        get("/teams/:teamId/members/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            int teamId = Integer.parseInt(request.params("teamId"));
            Team team = teamDao.findById(teamId);
            model.put("team", team);

            teamDao.deleteAllMembersOfTeam(teamId);

            return new ModelAndView(model, "member-success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete a team and all members
        get("/teams/:teamId/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            int teamId = Integer.parseInt(request.params("teamId"));
            Team team = teamDao.findById(teamId);
            model.put("team", team);

            teamDao.deleteAllMembersOfTeam(teamId);
            teamDao.deleteById(teamId);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete all teams and all members
        get("/teams/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            memberDao.clearAll();
            teamDao.clearAllTeams();

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //=====================================================================create routes
        //get: show form to add a new team
        get("/teams/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);
            return new ModelAndView(model, "team-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new team form
        post("/teams/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);
            String newTeamName = request.queryParams("name");
            String newTeamDescription = request.queryParams("description");
            Team newTeam = new Team(newTeamName, newTeamDescription);
            teamDao.add(newTeam);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show form to add member
        get("/teams/:teamId/members/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            int teamId = Integer.parseInt(request.params("teamId"));
            Team team = teamDao.findById(teamId);
            model.put("team", team);

            return new ModelAndView(model, "member-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process form to add members
        post("/teams/:teamId/members/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);
            int teamId = Integer.parseInt(request.params("teamId"));
            Team team = teamDao.findById(teamId);
            model.put("team", team);
            String name = request.queryParams("name");
            String email = request.queryParams("email");
            String twitter = request.queryParams("twitter");
            Member newMember = new Member(name, email, twitter);
            newMember.setTeamId(teamId);
            memberDao.add(newMember);
            return new ModelAndView(model, "member-success.hbs");
        }, new HandlebarsTemplateEngine());


        //================================================================read routes
        //get: landing page
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            //add all data
            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);
            List<Member> members = memberDao.getAll();
            model.put("members", members);

            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show about
        get("/about", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "about.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show all teams
        get("/teams", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);
            return new ModelAndView(model, "teams.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a single team and list all members
        get("/teams/:teamId", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);
            int idOfTeam = Integer.parseInt(request.params("teamId"));
            Team team = teamDao.findById(idOfTeam);
            model.put("team", team);
            List<Member> members = teamDao.getAllMembersByTeam(idOfTeam);
            model.put("members", members);
            return new ModelAndView(model, "team-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a specific team member
        get("/teams/:teamId/members/:memberId", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            int teamId = Integer.parseInt(request.params("teamId"));
            Team team = teamDao.findById(teamId);
            model.put("team", team);

            int memberId = Integer.parseInt(request.params("memberId"));
            Member member = memberDao.findById(memberId);
            model.put("member", member);

            return new ModelAndView(model, "member-detail.hbs");
        }, new HandlebarsTemplateEngine());


        //============================================================================update routes
        //get: show form to update team name/description
        get("/teams/:teamId/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            int teamId = Integer.parseInt(request.params("teamId"));
            Team updateTeam = teamDao.findById(teamId);
            model.put("updateTeam", updateTeam);

            return new ModelAndView(model, "team-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process update form
        post("/teams/:teamId/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            int teamId = Integer.parseInt(request.params("teamId"));
            Team updateTeam = teamDao.findById(teamId);

            String newName = request.queryParams("name");
            String newDescription = request.queryParams("description");
            teamDao.update(teamId, newName, newDescription);

            return new ModelAndView(model,"success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a member
        get("/teams/:teamId/members/:memberId/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            int teamId = Integer.parseInt(request.params("teamId"));
            Team team = teamDao.findById(teamId);
            model.put("team", team);

            int memberId = Integer.parseInt(request.params("memberId"));
            Member updateMember = memberDao.findById(memberId);
            model.put("updateMember", updateMember);

            return new ModelAndView(model, "member-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process update member form
        post("/teams/:teamId/members/:memberId/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            List<Team> teams = teamDao.getAllAlphabetized();
            model.put("teams", teams);

            int teamId = Integer.parseInt(request.params("teamId"));
            Team team = teamDao.findById(teamId);
            model.put("team", team);

            int memberId = Integer.parseInt(request.params("memberId"));
            Member updateMember = memberDao.findById(memberId);
            model.put("updateMember", updateMember);

            String newName = request.queryParams("newName");
            String newEmail = request.queryParams("newEmail");
            String newTwitter = request.queryParams("newTwitter");
            memberDao.update(memberId, newName, newEmail, newTwitter, teamId);

            return new ModelAndView(model, "member-success.hbs");
        }, new HandlebarsTemplateEngine());

    }
}