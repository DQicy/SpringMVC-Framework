package com.mooc.ydq.controllers;

import com.mooc.ydq.beans.AutoWired;
import com.mooc.ydq.service.SalaryService;
import com.mooc.ydq.web.mvc.Controller;
import com.mooc.ydq.web.mvc.RequestMapping;
import com.mooc.ydq.web.mvc.RequestParam;

@Controller
public class SalaryController {
    @AutoWired
    private SalaryService salaryService;
    //功能：计算工资（通过员工姓名和工龄来计算）
    @RequestMapping("/get_salary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience){
        return salaryService.calSalary(Integer.parseInt(experience));
    }

}
