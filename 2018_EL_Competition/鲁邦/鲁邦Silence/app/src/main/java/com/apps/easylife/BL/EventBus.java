package com.apps.easylife.BL;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class EventBus {

    public EventBus() {
    }

    private PublishSubject<Object> subject = PublishSubject.create();

    /**
     * 将事件传递给监听器
     */
    public void send(Object o) {
        subject.onNext(o);
    }

    /**
     * 监听事件并处理
     */
    public Observable<Object> getEvents() {
        return subject;
    }
}
