package edu.puj.procesobbva.sor.controller.vm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Streamable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Getter
@Setter
public class AnswerListVM implements Streamable<AnswerVM>, Serializable {
    private static final long serialVersionUID = 5253641214356368109L;

    private Long code;
    private Long sequence;
    private List<AnswerVM> answerList;

    @Override
    public Iterator<AnswerVM> iterator() {
        return this.answerList.iterator();
    }

    @Override
    public void forEach(Consumer<? super AnswerVM> action) {
        this.answerList.forEach(action);
    }

    @Override
    public Spliterator<AnswerVM> spliterator() {
        return this.answerList.spliterator();
    }
}
