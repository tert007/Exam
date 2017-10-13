package com.greenkeycompany.exam.repository.realm;

/**
 * Created by tert0 on 11.10.2017.
 */

public interface UnixTimeRealmObject {

    String FILED_UNIX_TIME = "unix_time";

    long getUnixTime();
    void setUnixTime(long unixTime);
}
