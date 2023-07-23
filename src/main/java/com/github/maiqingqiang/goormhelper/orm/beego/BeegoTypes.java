package com.github.maiqingqiang.goormhelper.orm.beego;

import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.inspections.GoTypeSpecDescriptor;
import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.inspections.core.GoCallableDescriptorSet;
import com.goide.inspections.core.GoMethodDescriptor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public interface BeegoTypes {
    Map<GoCallableDescriptor, Integer> CALLABLES = Map.<GoCallableDescriptor, Integer>ofEntries(
            // https://github.com/beego/beego/blob/420e11ee6380c4f74bba9d0c0e1b4d43fc5de960/client/orm/types.go#L272
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).Filter"), 0),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).FilterRaw"), 0),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).Exclude"), 0),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).GroupBy"), 0),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).OrderBy"), 0),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).UpdateWithCtx"), 1),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).ValuesFlat"), 1),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).ValuesFlatWithCtx"), 2),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).RowsToMap"), 1),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.QuerySeter).RowsToStruct"), 1)
    );

    GoCallableDescriptorSet CALLABLES_SET = new GoCallableDescriptorSet(CALLABLES.keySet());


    Map<GoCallableDescriptor, Integer> SCHEMA_CALLABLES = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.DQL).QueryTable"), 0),
            Map.entry(GoMethodDescriptor.of("(github.com/beego/beego/v2/client/orm.DQL).QueryTableWithCtx"), 1)
    );

    GoCallableDescriptorSet SCHEMA_CALLABLES_SET = new GoCallableDescriptorSet(SCHEMA_CALLABLES.keySet());

    Map<GoCallableDescriptor, List<String>> QUERY_EXPR = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Where"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Wheref"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOr"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).All"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).One"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Array"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Value"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Count"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindAll"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindOne"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindArray"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindValue"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindCount"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindScan"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Having"), Types.OPERATOR_EXPR)
    );

    Set<GoCallableDescriptor> ALLOW_TYPES = Set.of(
            GoTypeSpecDescriptor.of("github.com/gogf/gf/database/gdb.Model")
    );

    // http://beego.gocn.vip/beego/zh/developing/orm/model.html#comment
    Pattern COMMENT_PATTERN = Pattern.compile("description\\((.*?)\\)");
    // http://beego.gocn.vip/beego/zh/developing/orm/model.html#%E5%88%97
    Pattern COLUMN_PATTERN = Pattern.compile("column\\((.*?)\\)");


}
