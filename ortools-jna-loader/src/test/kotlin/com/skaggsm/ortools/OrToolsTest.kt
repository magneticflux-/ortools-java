package com.skaggsm.ortools

import com.google.ortools.linearsolver.MPSolver
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */
class OrToolsTest : StringSpec({
    "Library is found" {
        JnaOrToolsHelper.loadLibrary()
    }
    "SimpleLpProgram example runs" {
        JnaOrToolsHelper.loadLibrary()

        // Create the linear solver with the GLOP backend.
        val solver = MPSolver("SimpleLpProgram", MPSolver.OptimizationProblemType.GLOP_LINEAR_PROGRAMMING)

        // Create the variables x and y.
        val x = solver.makeNumVar(0.0, 1.0, "x")
        val y = solver.makeNumVar(0.0, 2.0, "y")

        solver.numVariables() shouldBe 2

        // Create a linear constraint, 0 <= x + y <= 2.
        val ct = solver.makeConstraint(0.0, 2.0, "ct")
        ct.setCoefficient(x, 1.0)
        ct.setCoefficient(y, 1.0)

        solver.numConstraints() shouldBe 1

        // Create the objective function, 3 * x + y.
        val objective = solver.objective()
        objective.setCoefficient(x, 3.0)
        objective.setCoefficient(y, 1.0)
        objective.setMaximization()

        solver.solve()

        objective.value() shouldBe 4.0
        x.solutionValue() shouldBe 1.0
        y.solutionValue() shouldBe 1.0
    }
})
