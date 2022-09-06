package com.example.crud_demo.event;

import com.example.crud_demo.entity.UserEntity;

public class MessageEvent {
    public static class Event {
        private UserEntity entity;

        public Event(UserEntity entity) {
            this.entity = entity;
        }

        public UserEntity getEntity() {
            return entity;
        }

        public void setEntity(UserEntity entity) {
            this.entity = entity;
        }
    }
}
