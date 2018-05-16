package com.xingtan.school.entity;

import com.xingtan.common.entity.BaseEntity;
import com.xingtan.common.entity.StudentDuty;
import lombok.*;

/**
 * 学生和班级的关系
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class StudentGradeRelation extends BaseEntity {

    /**
     * 学生ID
     */
    private long studentId;

    /**
     * 班级ID
     */
    private long gradeId;

    /**
     * 职务
     */
    private StudentDuty duty = StudentDuty.NONE;

    /**
     * 别名
     */
    private String alias;
    /**
     * 学号
     */
    private String no;

    public StudentGradeRelation(long studentId, long gradeId) {

    }
}
