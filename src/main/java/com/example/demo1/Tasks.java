package com.example.demo1;

public record  Tasks(
    int task_id,
    String taskname,
    String task_desc,
    int task_level,
    int user_id
) {

}
