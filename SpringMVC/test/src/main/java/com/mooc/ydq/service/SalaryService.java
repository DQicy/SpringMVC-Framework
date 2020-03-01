package com.mooc.ydq.service;

import com.mooc.ydq.beans.Bean;

@Bean
public class SalaryService {

    public Integer calSalary(Integer experience){
            return experience * 5000;
    }
}
