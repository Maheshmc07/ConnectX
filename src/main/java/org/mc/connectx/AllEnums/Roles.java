package org.mc.connectx.AllEnums;

import java.util.HashSet;
import java.util.Set;

public enum Roles {

    ADMIN(Set.of(Permissions.READ,Permissions.DELETE,Permissions.WRITE,Permissions.UPDATE)),
    USER(Set.of(Permissions.READ,Permissions.DELETE)),
    GUEST(Set.of(Permissions.READ));
     Set<Permissions> permissionSet = new HashSet<>();

    public Set<Permissions> getPermissionSet() {
        return permissionSet;
    }

    Roles(Set<Permissions> permissionSet) {
        this.permissionSet = permissionSet;
    }
}
