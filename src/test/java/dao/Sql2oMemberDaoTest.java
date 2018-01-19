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
}