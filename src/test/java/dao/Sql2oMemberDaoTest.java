package dao;

import models.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oMemberDaoTest {

    private Sql2oMemberDao memberDao;
    private Connection testConnection;

    Member setupMember() {
        return new Member("Frank", "hitmeup@notemail.com","@therealfred");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        memberDao = new Sql2oMemberDao(sql2o);

        testConnection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        testConnection.close();
    }

    @Test
    public void add_AssignsUniqueId_true() {
        Member testMember = setupMember();
        int initialId = testMember.getId();
        memberDao.add(testMember);
        assertNotEquals(initialId, testMember.getId());

    }

    @Test
    public void getAll_returnsAllInstancesOfMember_true() {
        Member testMember = setupMember();
        Member secondTestMember = setupMember();
        memberDao.add(testMember);
        memberDao.add(secondTestMember);
        assertEquals(2, memberDao.getAll().size());
    }

    @Test
    public void findById_returnsMemberInstanceWithCorrespondingId_true() {
        Member testMember = setupMember();
        Member secondTestMember = setupMember();
        secondTestMember.setName("this test passes");
        memberDao.add(testMember);
        memberDao.add(secondTestMember);
        assertEquals("Frank", memberDao.findById(1).getName());
        assertEquals("this test passes", memberDao.findById(2).getName());
    }

    @Test
    public void update_ChangesDataForSpecifiedId_true() {
        Member testMember = setupMember();
        Member controlMember = setupMember();
        memberDao.add(testMember);
        memberDao.add(controlMember);
        memberDao.update(1, "this test passes", "booyah@test.passes", "@pass", 1);
        assertEquals("Frank", memberDao.findById(2).getName());
        assertEquals("this test passes", memberDao.findById(1).getName());
        assertEquals("booyah@test.passes", memberDao.findById(1).getEmail());
        assertEquals("@pass", memberDao.findById(1).getTwitter());
        assertEquals(1, memberDao.findById(1).getTeamId());
    }

    @Test
    public void deleteById_deletesMemberInstanceForSpecifiedId_true() {
        Member testMember = setupMember();
        Member secondMember = setupMember();
        Member deletedMember = setupMember();
        memberDao.add(testMember);
        memberDao.add(secondMember);
        memberDao.add(deletedMember);
        assertEquals(3, memberDao.getAll().size());
        memberDao.deleteById(3);
        assertEquals(2, memberDao.getAll().size());
        assertFalse(memberDao.getAll().contains(deletedMember));
    }

    @Test
    public void clearAll_removesAllInstancesOfMember_true() {
        Member testMember = setupMember();
        Member secondMember = setupMember();
        Member thirdMember = setupMember();
        memberDao.add(testMember);
        memberDao.add(secondMember);
        memberDao.add(thirdMember);
        assertEquals(3, memberDao.getAll().size());
        memberDao.clearAll();
        assertEquals(0, memberDao.getAll().size());
    }
}