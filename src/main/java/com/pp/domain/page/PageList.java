package com.pp.domain.page;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by astepanov
 */
@Getter
@Setter
public final class PageList<T> {

    private int start = 1;
    private int end = 1;
    private final int buttonsToDisplay;
    private final PageWrapper<T> currentPage;

    public PageList(final PageWrapper<T> currentPage, final int buttonsToDisplay) {
        this.currentPage = currentPage;
        this.buttonsToDisplay = buttonsToDisplay;
    }

    public void initializeBounds() throws IllegalArgumentException {
        if(this.buttonsToDisplay %2 == 0){
            throw new IllegalArgumentException("amount of buttons must be odd");
        }
        final int currentNumber = this.currentPage.getNumber() + 1;
            this.start = currentNumber - this.buttonsToDisplay/2;
            this.end = currentNumber + this.buttonsToDisplay/2;
            if(this.start < 1){
                this.start = 1;
            }
            if(this.end > this.currentPage.getTotalPages()){
                this.end = this.currentPage.getTotalPages();
            }
    }

}
