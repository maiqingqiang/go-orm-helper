package com.github.maiqingqiang.goormhelper.orm.xorm;

import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.inspections.GoTypeSpecDescriptor;
import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.inspections.core.GoCallableDescriptorSet;
import com.goide.inspections.core.GoMethodDescriptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface XormTypes {
    Map<GoCallableDescriptor, Integer> CALLABLES = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).Where"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Where"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).And"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Asc"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Desc"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).Select"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Select"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).In"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).In"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Or"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).Cols"), -1),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Cols"), -1),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).Omit"), -1),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Omit"), -1),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).Distinct"), -1),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Distinct"), -1),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).GroupBy"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).GroupBy"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).Having"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Having"), 0)
    );

    GoCallableDescriptorSet CALLABLES_SET = new GoCallableDescriptorSet(CALLABLES.keySet());

    Map<GoCallableDescriptor, Integer> SCHEMA_CALLABLES = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).Table"), 0)
    );

    GoCallableDescriptorSet SCHEMA_CALLABLES_SET = new GoCallableDescriptorSet(SCHEMA_CALLABLES.keySet());


    Map<GoCallableDescriptor, Integer> OTHER_SCHEMA_CALLABLES = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Get"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Find"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Count"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Iterate"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Rows"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Sum"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Update"), 0),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Delete"), 0)
    );

    GoCallableDescriptorSet OTHER_CALLABLES_SET = new GoCallableDescriptorSet(OTHER_SCHEMA_CALLABLES.keySet());

    Map<GoCallableDescriptor, List<String>> QUERY_EXPR = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Engine).Where"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Where"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).And"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*xorm.io/xorm.Session).Or"), Types.OPERATOR_EXPR)
    );

    Set<GoCallableDescriptor> ALLOW_TYPES = Set.of(
            GoTypeSpecDescriptor.of("xorm.io/xorm.Session")
    );
}
