package com.xingtan.school.web;

import com.xingtan.school.entity.School;
import com.xingtan.school.service.SchoolService;
import com.xingtan.common.web.BaseResponse;
import com.xingtan.common.web.HttpStatus;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学校
 */
@RestController
@Slf4j
@RequestMapping(value = "/api/school", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping("/query")
    @ApiOperation(value = "通过名获取学校", notes = "通过名获取学校", httpMethod = "GET")
    @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "操作成功")
    })
    public BaseResponse getSchoolsByName(@RequestParam("name") String name) {
        List<School> schools = null;
        try {
            schools = schoolService.getSchoolByName(name);
        } catch (Exception e) {
            return new BaseResponse<School>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
        return new BaseResponse<List<School>>(HttpStatus.OK, schools);
    }
    @GetMapping("/{schoolId}")
    @ApiOperation(value = "通过ID获取学校", notes = "通过ID获取学校", httpMethod = "GET")
    @ApiImplicitParam(name = "schoolId", value = "ID", required = true, dataType = "Long", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "操作成功")
    })
    public BaseResponse getSchoolsById(@PathVariable("schoolId") Long schoolId) {
        School school = null;
        try {
            school = schoolService.getSchoolById(schoolId);
        } catch (Exception e) {
            return new BaseResponse<School>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
        return new BaseResponse<>(HttpStatus.OK, school);
    }
    @PostMapping("/add")
    @ApiOperation(value = "添加学校", notes = "添加学校", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户", required = true, dataType = "School", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "参数不全"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部错误"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "操作成功")
    })
    public BaseResponse addSchool(@RequestBody School school) {
        try {
            log.info("add School, {}", school);
            school.setIntroduce(Strings.EMPTY);
            school.setCreatedUserId(1);
            schoolService.insertSchool(school);
            log.info("add School, SUCCESS");
        } catch (Exception e) {
            log.error("add School Error, school:{}, error:{}", school, e);
            return new BaseResponse<School>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
        return new BaseResponse<School>(HttpStatus.OK, school);
    }


}
