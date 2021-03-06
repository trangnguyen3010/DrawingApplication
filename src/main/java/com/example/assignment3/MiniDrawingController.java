package com.example.assignment3;

import javafx.scene.input.MouseEvent;

import java.lang.reflect.InvocationTargetException;

public class MiniDrawingController extends DrawingController{
    public MiniDrawingController(){
        super();
        currentState = State.READY;
    }

    private enum State {
        READY,
        MOVING,
    }

    private State currentState;

    public void handlePressed(Double normX, Double normY, MouseEvent event) {
        prevX = normX;
        prevY = normY;

        if (iModel.onViewFinder(normX, normY)) {
            currentState = State.MOVING;
        }
        else {
            super.handlePressed(normX, normY, event);
        }
    }

    public void handleDragged (Double normX, Double normY, MouseEvent event) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (currentState == State.MOVING){
                double dX = normX - prevX;
                double dY = normY - prevY;
                iModel.moveView(dX, dY);
                prevX = normX;
                prevY = normY;
        }
        else {
            super.handleDragged(normX, normY, event);
        }
    }

    public void handleReleased(MouseEvent event) {
        if (currentState == State.MOVING){
        currentState = State.READY;}
        super.handleReleased(event);
    }

    public void handleResizeView(double normWidth, double normHeight){
        iModel.updateView(iModel.viewLeft, iModel.viewTop, normWidth, normHeight);
    }
}
