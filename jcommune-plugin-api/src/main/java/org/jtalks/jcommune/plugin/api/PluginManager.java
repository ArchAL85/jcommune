/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jtalks.jcommune.plugin.api;

import org.jtalks.common.model.permissions.JtalksPermission;
import org.jtalks.jcommune.plugin.api.filters.PluginFilter;
import org.jtalks.jcommune.plugin.api.filters.TypeFilter;
import org.jtalks.jcommune.plugin.api.plugins.Plugin;
import org.jtalks.jcommune.plugin.api.plugins.PluginWithBranchPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages plugin's extensions.
 *
 * @author Evgeniy Myslovets
 */
public class PluginManager {

    private PluginLoader pluginLoader;

    /**
     * Constructs {@link PluginManager} with given {@link PluginLoader}
     *
     * @param pluginLoader plugin loader instance
     */
    public PluginManager(PluginLoader pluginLoader) {
        this.pluginLoader = pluginLoader;
    }

    /**
     * @return plugin branch permission for given branch
     */
    public List<JtalksPermission> getPluginsBranchPermissions() {
        PluginFilter filter = new TypeFilter(PluginWithBranchPermissions.class);
        List<Plugin> plugins = pluginLoader.getPlugins(filter);
        List<JtalksPermission> branchPermissions = new ArrayList<>();
        for (Plugin plugin : plugins) {
            if (plugin.isEnabled()) {
                branchPermissions.addAll(((PluginWithBranchPermissions) plugin).getBranchPermissions());
            }
        }
        return branchPermissions;
    }

    public JtalksPermission findPluginsBranchPermissionByMask(int mask) {
        PluginFilter filter = new TypeFilter(PluginWithBranchPermissions.class);
        List<Plugin> plugins = pluginLoader.getPlugins(filter);
        JtalksPermission permission = null;
        for (Plugin plugin : plugins) {
            if (plugin.isEnabled()) {
                permission = ((PluginWithBranchPermissions)plugin).getBranchPermissionByMask(mask);
                if (permission != null) {
                    return permission;
                }
            }
        }
        return permission;
    }

    public JtalksPermission findPluginsBranchPermissionByName(String name) {
        PluginFilter filter = new TypeFilter(PluginWithBranchPermissions.class);
        List<Plugin> plugins = pluginLoader.getPlugins(filter);
        JtalksPermission permission = null;
        for (Plugin plugin : plugins) {
            if (plugin.isEnabled()) {
                permission = ((PluginWithBranchPermissions)plugin).getBranchPermissionByName(name);
                if (permission != null) {
                    return permission;
                }
            }
        }
        return permission;
    }
}
