package me.stevenkin.boom.token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoleList implements Iterable<Role> {
    private List<Role> roles = new ArrayList<>();

    public RoleList addRole(Role role) {
        roles.add(role);
        return this;
    }

    @Override
    public Iterator<Role> iterator() {
        return roles.iterator();
    }
}
