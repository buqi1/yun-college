package com.buqi.msm.service;

import java.util.Map;

public interface MsmService {
    boolean send(Map<String, String> param, String phone);
}
