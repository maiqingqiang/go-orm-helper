package com.github.maiqingqiang.goormhelper.orm.goframe;

import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.inspections.GoTypeSpecDescriptor;
import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.inspections.core.GoCallableDescriptorSet;
import com.goide.inspections.core.GoFunctionDescriptor;
import com.goide.inspections.core.GoMethodDescriptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GoFrameTypes {
    Map<GoCallableDescriptor, Integer> CALLABLES = Map.<GoCallableDescriptor, Integer>ofEntries(
            // @Doc https://goframe.org/pages/viewpage.action?pageId=1114344
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).OnDuplicate"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).OnDuplicateEx"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Insert"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).InsertIgnore"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).InsertAndGetId"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Replace"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Save"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Data"), 0),

            // @Doc https://goframe.org/pages/viewpage.action?pageId=1114238
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Update"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Increment"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Decrement"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Delete"), 0),

            // @Doc https://goframe.org/pages/viewpage.action?pageId=7301832#ORM%E6%9F%A5%E8%AF%A2Where/WhereOr/WhereNot-WherePri%E6%94%AF%E6%8C%81%E4%B8%BB%E9%94%AE%E7%9A%84%E6%9F%A5%E8%AF%A2%E6%9D%A1%E4%BB%B6
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Where"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Wheref"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereBetween"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereLike"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereIn"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereNull"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereLT"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereLTE"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereGT"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereGTE"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereNotBetween"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereNotLike"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereNotIn"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereNotNull"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOr"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrBetween"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrLike"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrIn"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrNull"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrLT"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrLTE"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrGT"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrGTE"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrNotBetween"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrNotLike"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrNotIn"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).WhereOrNotNull"), -1),

            // @Doc https://goframe.org/pages/viewpage.action?pageId=7301838
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).All"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).One"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Array"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Value"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Count"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).CountColumn"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindAll"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindOne"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindArray"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindValue"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindCount"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).FindScan"), 1),

            // @Doc https://goframe.org/pages/viewpage.action?pageId=7301860
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Fields"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Group"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Order"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).OrderAsc"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).OrderDesc"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Having"), 0),

            // @Doc https://goframe.org/pages/viewpage.action?pageId=7301867
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Min"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Max"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Avg"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/gogf/gf/database/gdb.Model).Sum"), 0)
    );

    GoCallableDescriptorSet CALLABLES_SET = new GoCallableDescriptorSet(CALLABLES.keySet());


    Map<GoCallableDescriptor, Integer> SCHEMA_CALLABLES = Map.ofEntries(
            Map.entry(GoFunctionDescriptor.of("github.com/gogf/gf/frame/g.Model"), 0),
            Map.entry(GoMethodDescriptor.of("(github.com/gogf/gf/database/gdb.DB).Model"), 0)
    );

    List<String> OTHER_SCHEMA_CALLABLES = List.of(
            "Ctx",
            "Table"
    );

    List<String> ALLOW_FIELDS = List.of(
            "columns"
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

    GoTypeSpecDescriptor G_META = GoTypeSpecDescriptor.of("github.com/gogf/gf/frame/g.Meta");
    GoTypeSpecDescriptor GMETA_META = GoTypeSpecDescriptor.of("github.com/gogf/gf/util/gmeta.Meta");
}
