package com.github.maiqingqiang.goormhelper.orm.gorm;

import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.inspections.GoTypeSpecDescriptor;
import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.inspections.core.GoCallableDescriptorSet;
import com.goide.inspections.core.GoMethodDescriptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GormTypes {
    Map<GoCallableDescriptor, Integer> CALLABLES = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Where"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Select"), -1),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Distinct"), -1),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).First"), 1),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Find"), 1),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Not"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Or"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Order"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Group"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Having"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Attrs"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Pluck"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Omit"), -1),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).UpdateColumn"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Update"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Delete"), 1),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Where"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Select"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Distinct"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).First"), 1),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Find"), 1),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Not"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Or"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Order"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Group"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Having"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Attrs"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Pluck"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Omit"), -1),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).UpdateColumn"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Update"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Delete"), 1)
    );

    GoCallableDescriptorSet CALLABLES_SET = new GoCallableDescriptorSet(CALLABLES.keySet());

    Map<GoCallableDescriptor, Integer> SCHEMA_CALLABLES = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Model"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Table"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Model"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Table"), 0)
    );

    GoCallableDescriptorSet SCHEMA_CALLABLES_SET = new GoCallableDescriptorSet(SCHEMA_CALLABLES.keySet());

    Map<GoCallableDescriptor, Integer> OTHER_SCHEMA_CALLABLES = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).First"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Find"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Delete"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).FirstOrInit"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).FirstOrCreate"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Take"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Last"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Create"), 0),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Save"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).First"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Find"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Delete"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).FirstOrInit"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).FirstOrCreate"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Take"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Last"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Create"), 0),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Save"), 0)
    );
    GoCallableDescriptorSet OTHER_SCHEMA_CALLABLES_SET = new GoCallableDescriptorSet(OTHER_SCHEMA_CALLABLES.keySet());

    Map<GoCallableDescriptor, List<String>> QUERY_EXPR = Map.ofEntries(
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Where"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*gorm.io/gorm.DB).Having"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Where"), Types.OPERATOR_EXPR),
            Map.entry(GoMethodDescriptor.of("(*github.com/jinzhu/gorm.DB).Having"), Types.OPERATOR_EXPR)
    );

    Set<GoCallableDescriptor> ALLOW_TYPES = Set.of(
            GoTypeSpecDescriptor.of("gorm.io/gorm.DB"),
            GoTypeSpecDescriptor.of("github.com/jinzhu/gorm.DB")
    );

}
