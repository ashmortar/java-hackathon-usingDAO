package dao;

import models.Member;
import models.Team;

import java.util.List;

public interface TeamDao {

    //create
    void add(Team team);

    //read
    List<Team> getAll();
    Team findById(int id);
    List<Team> getAllAlphabetized();
    List<Member> getAllMembersByTeam(int teamId);

    //update
    void update(int id, String newName, String newDescription);

    //delete
    void deleteById(int id);
    void clearAllTeams();
    void deleteAllMembersOfTeam(int teamId);
}
