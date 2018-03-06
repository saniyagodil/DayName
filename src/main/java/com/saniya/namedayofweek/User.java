package com.saniya.namedayofweek;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String password;

    @ManyToMany(fetch =FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;

    @ManyToMany
    private Set<BirthInfo> birthInfo;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String username, String password) {
        this.roles = new HashSet<>();
        this.username = username;
        this.password = password;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }


    public Set<BirthInfo> getBirthInfo() {
        return birthInfo;
    }

    public void setBirthInfo(Set<BirthInfo> birthInfo) {
        this.birthInfo = birthInfo;
    }
    public void addBirthInfo(BirthInfo birthInfo){
        this.birthInfo.add(birthInfo);
    }

    public BirthInfo getRecentBirthInfo(){
        BirthInfo newBirthInfo = new BirthInfo();
        for(BirthInfo birthInfo: this.birthInfo){
            if(birthInfo.getDay().length() >= 1){
                newBirthInfo = birthInfo;
            }
        }
        return newBirthInfo;
    }


}