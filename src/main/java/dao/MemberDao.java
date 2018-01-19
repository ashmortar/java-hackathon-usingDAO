package dao;

import models.Member;

import java.util.List;

public interface MemberDao {

    //create
    void add(Member member);

    //read
    List<Member> getAll();
    Member findById(int id);

    //update
     void update(int id, String newName, String newEmail, String newTwitter, int newTeamId);

    //delete
    void deleteById(int id);
    void clearAll();
}
