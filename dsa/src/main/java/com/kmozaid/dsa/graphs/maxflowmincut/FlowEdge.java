package com.kmozaid.dsa.graphs.maxflowmincut;

public class FlowEdge {

    // to deal with floating-point roundoff errors
    private static final double FLOATING_POINT_EPSILON = 1E-10;

    private final int v;             // from
    private final int w;             // to
    private final double capacity;   // capacity
    private double flow;             // flow

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
    }

    public FlowEdge(int v, int w, double capacity, double flow) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = flow;
    }

    public FlowEdge(FlowEdge e) {
        this.v = e.v;
        this.w = e.w;
        this.capacity = e.capacity;
        this.flow = e.flow;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public int other(int vertex) {
        if (vertex == v)
            return w;
        else if (vertex == w)
            return v;
        else
            throw new IllegalArgumentException("invalid endpoint");
    }

    public double residualCapacityTo(int vertex) {
        if (vertex == v)
            return flow;              // backward edge
        else if (vertex == w)
            return capacity - flow;   // forward edge
        else
            throw new IllegalArgumentException("invalid endpoint");
    }

    /**
     * Increases the flow on the edge in the direction to the given vertex.
     * If vertex is the tail vertex, this increases the flow on the edge by delta;
     * if vertex is the head vertex, this decreases the flow on the edge by delta.
     */
    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex == v)
            flow -= delta;           // backward edge
        else if (vertex == w)
            flow += delta;           // forward edge
        else
            throw new IllegalArgumentException("invalid endpoint");

        // round flow to 0 or capacity if within floating-point precision
        if (Math.abs(flow) <= FLOATING_POINT_EPSILON)
            flow = 0;
        if (Math.abs(flow - capacity) <= FLOATING_POINT_EPSILON)
            flow = capacity;

        if (flow < 0.0)
            throw new IllegalArgumentException("Flow is negative");
        if (flow > capacity)
            throw new IllegalArgumentException("Flow exceeds capacity");
    }


    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }

}
