package com.apps.easylife.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Session {
    @PrimaryKey
    int id;
}
