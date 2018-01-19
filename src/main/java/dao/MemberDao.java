package dao;

import models.Member;

import java.util.List;

public interface MemberDao {

    //create
    void add(Member member);

    //read
    List<Member> getAll();
    //Member findById(int id);

    //update
    // void update(int id, String newName, String newEmail, int newTeamId);

    //delete
    //void deleteByIf(int id);
    //void clearAll();
}
