# Genetic Algorithms

A collection of evolutionary computation implementations in Java, including genetic algorithms (GA), evolution strategies (ES), population-based incremental learning (PBIL), compact GA (cGA), and genetic programming (GP) examples.

## Requirements

- **Java** (JDK 8 or later)
- **SymbolicRegressionGA-3** and **Examples/GP-EpochX-1**: EpochX library for genetic programming

## Project Structure

| Project | Description |
|--------|-------------|
| **GeneticAlgorithm-1** | Simple genetic algorithm (SGA) with binary genomes. Supports roulette wheel, stochastic universal sampling (SUS), and tournament selection. |
| **BinaryKnapsack-5** | Binary knapsack problem solved with GA, compact GA (cGA), and PBIL. Includes multiple benchmark instances (100–10000 items). |
| **CryptoarithmProblem-4** | Cryptoarithmetic puzzles solved with a genetic algorithm. Includes *Send+More=Money*, *Two+Two=Four*, *To+Go=Out*, and *Brown+Yellow=Purple*. |
| **EvolutionStrategy-2** | Evolution strategy (ES) with (μ,λ) selection. Includes ES with n σ (SetESNSigma) for adaptive step sizes. |
| **GAP-6** | Generalized Assignment Problem: assign tasks to processors minimizing cost subject to capacity constraints. Uses SGA with problem instances from `Gap Files/`. |
| **SymbolicRegressionGA-3** | Symbolic regression using genetic programming (EpochX). Q1: function approximation from I/O files; Q2: extended model. |
| **Examples** | External library examples: **GAUsingECJ** (ECJ framework), **GP-EpochX** (EpochX GP: XOR, multiplexer, symbolic regression). |

## Quick Start

Each project is self-contained. Compile and run from the project’s `src` directory (or as configured in your IDE).

### GeneticAlgorithm-1 (SGA)

```bash
cd GeneticAlgorithm-1/src
javac sga/*.java
java sga.SGATest
```

Output is written to `TournamentFinal.txt` (or similar, depending on selection method).

### BinaryKnapsack-5

```bash
cd BinaryKnapsack-5/src
javac *.java
java GATest
```

Runs the GA on all knapsack instances under `knapsack_data_instances/` and compares to `knapsack_optimum/` when available.

### CryptoarithmProblem-4

```bash
cd CryptoarithmProblem-4/src
javac *.java
java SendMoreMoney
# or: java TwoTwoFour, java ToGoOut, java BrownYellowPurple
```

### EvolutionStrategy-2

```bash
cd EvolutionStrategy-2/src
javac *.java
java Test
```

Uses dimension D=10 and population 200 by default; parameters can be changed in `Test.java`.

### GAP-6 (Generalized Assignment Problem)

Place input file (e.g. `gap1.txt`) as specified in `SGATest.java`, then:

```bash
cd GAP-6/src
javac *.java
java SGATest
```

Input files live in `GAP-6/Gap Files/` (e.g. `gap1.txt`–`gap5.txt`).

### SymbolicRegressionGA-3

Requires EpochX on the classpath. Uses `Q1_input.txt` / `Q1_output.txt` and `Q2_input.txt` / `Q2_output.txt` in the project directory.

```bash
cd SymbolicRegressionGA-3/src
# Compile with EpochX in classpath, then:
java Q1_Example
# or java Q2_Example
```

## Selection Methods (GeneticAlgorithm-1)

- **Roulette wheel** – fitness-proportional selection  
- **Stochastic universal sampling (SUS)** – spread selection across the population  
- **Tournament** – k-way tournament (e.g. size 5)

Toggle in `SGA.java` by commenting/uncommenting the relevant parent-selection block.

## File Layout (typical)

- **`src/`** – Java sources; often run from this directory so paths to data files are correct.  
- **Data** – Knapsack: `knapsack_data_instances/`, `knapsack_optimum/`; GAP: `Gap Files/`; Symbolic regression: `Q1_input.txt`, `Q1_output.txt`, etc.

## License

See repository or project-level license files for terms of use.
