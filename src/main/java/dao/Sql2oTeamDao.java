package dao;

import org.sql2o.Sql2o;

public class Sql2oTeamDao implements TeamDao{

    private final Sql2o sql2o;
    public Sql2oTeamDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

}
