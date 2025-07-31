package org.khr.demo.dao.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 *  表定义层。
 *
 * @author KK
 * @since 2025-07-31 14:13:27
 */
public class SysRolePermissionTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SysRolePermissionTableDef SYS_ROLE_PERMISSION = new SysRolePermissionTableDef();

    /**
     * 角色ID
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 权限ID
     */
    public final QueryColumn PERMISSION_ID = new QueryColumn(this, "permission_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ROLE_ID, PERMISSION_ID};

    public SysRolePermissionTableDef() {
        super("", "sys_role_permission");
    }

    private SysRolePermissionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SysRolePermissionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SysRolePermissionTableDef("", "sys_role_permission", alias));
    }

}
