package dao;

import models.Member;
import models.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Sql2oTeamDaoTest {

    private Sql2oTeamDao teamDao;
    private Sql2oMemberDao memberDao;
    private Connection testConnection;

    Member setupMember() {
        return new Member("Frank", "hitmeup@notemail.com","@therealfred");
    }

    Team setupTeam() {
        return new Team ("Test Team", "Test Description");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        memberDao = new Sql2oMemberDao(sql2o);
        teamDao = new Sql2oTeamDao(sql2o);

        testConnection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        testConnection.close();
    }

    @Test
    public void add_placesTeamInstanceInDBAndAssignsUniqueId_true() {
        Team testTeam = setupTeam();
        int initialId = testTeam.getId();
        teamDao.add(testTeam);
        assertNotEquals(initialId, testTeam.getId());
    }

    @Test
    public void getAll_returnsAllInstancesOfTeamInDB_true() {
        Team testTeam = setupTeam();
        Team secondTestTeam = setupTeam();
        Team controlTeam = setupTeam();
        teamDao.add(testTeam);
        teamDao.add(secondTestTeam);
        assertEquals(2, teamDao.getAll().size());
        assertFalse(teamDao.getAll().contains(controlTeam));
    }

    @Test
    public void findById_returnsTeamWithSpecifiedId_true() {
        Team testTeam = setupTeam();
        Team controlTeam = setupTeam();
        testTeam.setName("the test passes");
        teamDao.add(testTeam);
        teamDao.add(controlTeam);
        assertEquals("the test passes", teamDao.findById(1).getName());
        assertEquals("Test Team", teamDao.findById(2).getName());
    }

    @Test
    public void getAllAlphabetized_returnsAllTeamInstancesInAlphabeticalOrderByName_true() {
        Team testTeamA = setupTeam();
        testTeamA.setName("A");
        Team tesTeamB = setupTeam();
        tesTeamB.setName("B");
        Team testTeamC = setupTeam();
        testTeamC.setName("C");
        teamDao.add(testTeamC);
        teamDao.add(tesTeamB);
        teamDao.add(testTeamA);
        List<Team> expectedOrder = new ArrayList<>();
        expectedOrder.add(testTeamA);
        expectedOrder.add(tesTeamB);
        expectedOrder.add(testTeamC);
        assertTrue(expectedOrder.equals(teamDao.getAllAlphabetized()));
    }

    @Test
    public void getAllMembersByTeam_returnsAllMemberInstancesWithCorrespondingTeamId_true() {
        Team testTeam = setupTeam();
        Team controlTeam = setupTeam();
        teamDao.add(testTeam);
        teamDao.add(controlTeam);
        Member testMember = setupMember();
        testMember.setTeamId(1);
        Member secondTestMember = setupMember();
        secondTestMember.setTeamId(1);
        Member controlMember = setupMember();
        controlMember.setTeamId(2);
        memberDao.add(testMember);
        memberDao.add(secondTestMember);
        memberDao.add(controlMember);
        assertEquals(2, teamDao.getAllMembersByTeam(1).size());
        assertTrue(teamDao.getAllMembersByTeam(1).contains(testMember));
        assertTrue(teamDao.getAllMembersByTeam(1).contains(secondTestMember));
        assertEquals(1, teamDao.getAllMembersByTeam(2).size());
        assertTrue(teamDao.getAllMembersByTeam(2).contains(controlMember));
    }

    @Test
    public void update_changesValuesForSpecifiedTeamInstance_true() {
        Team testTeam = setupTeam();
        Team controlTeam = setupTeam();
        teamDao.add(testTeam);
        teamDao.add(controlTeam);
        teamDao.update(1, "THIS TEST", "PASSES");
        assertEquals("Test Team", teamDao.findById(2).getName());
        assertEquals("Test Description", teamDao.findById(2).getDescription());
        assertEquals("THIS TEST", teamDao.findById(1).getName());
        assertEquals("PASSES", teamDao.findById(1).getDescription());
    }

    @Test
    public void deleteById_removesSpecifiedTeamInstance_true() {
        Team testTeam = setupTeam();
        Team controlTeam = setupTeam();
        teamDao.add(testTeam);
        teamDao.add(controlTeam);
        assertEquals(2, teamDao.getAll().size());
        teamDao.deleteById(1);
        assertEquals(1, teamDao.getAll().size());
        assertFalse(teamDao.getAll().contains(testTeam));
    }

    @Test
    public void clearAllTeams_removesAllTestInstances_true() {
        Team testTeam = setupTeam();
        Team secondTestTeam = setupTeam();
        Team thirdtestTeam = setupTeam();
        teamDao.add(testTeam);
        teamDao.add(secondTestTeam);
        teamDao.add(thirdtestTeam);
        assertEquals(3, teamDao.getAll().size());
        teamDao.clearAllTeams();
        assertEquals(0, teamDao.getAll().size());
    }

    @Test
    public void deleteAllMembersOfTeam_removesAllMembersWithCorrespondingTeamId_tru() {
        Team testTeam = setupTeam();
        Team controlTeam = setupTeam();
        teamDao.add(testTeam);
        teamDao.add(controlTeam);
        Member testMember1 = setupMember();
        testMember1.setTeamId(1);
        Member testMember2 = setupMember();
        testMember2.setTeamId(1);
        Member controlMember = setupMember();
        controlMember.setTeamId(2);
        memberDao.add(testMember1);
        memberDao.add(testMember2);
        memberDao.add(controlMember);
        assertEquals(2, teamDao.getAllMembersByTeam(1).size());
        assertEquals(1, teamDao.getAllMembersByTeam(2).size());
        teamDao.deleteAllMembersOfTeam(1);
        assertEquals(0, teamDao.getAllMembersByTeam(1).size());
        assertEquals(1, teamDao.getAllMembersByTeam(2).size());
        assertTrue(teamDao.getAllMembersByTeam(2).contains(controlMember));
    }
}