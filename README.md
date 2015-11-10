# ice-road
Ice road shipment scheduler example: having functional fun!

## The problem

There is a road open for 60 days in a year, and there is a need to transport good on that road. Here are some restrictions:
- The road can handle seven concurrent shipments every hour starting Feb 1st, 8:00 AM, 24/7
- During the first 15 days, the road can only handle shipments weighting up to 15,000 kg.
- The shipments have priority from 1 - 3 being 1 the top priority, but some shipment don't have any priority.

Create an scheduler for those shipments as follows:

- During the first 15 days, schedule shipments with less than 15,000 kg ordered by priority first and then lighter loads first.
- After the first 15 days, schedule shipments more than 15,000 kg first orderted by priority first and then weavier loads first.

The shipments are received as a CSV file (Ex. ./data/shipments.csv) and the output should be as a CSV file (Ex. ./data/schedules.csv)


## Proposed solution.

This problem could be solved by applying a pure function f to the shipments to get to the schedules:

```
schedules = f( shipments )
```

The input file is read from the stdin and the output file is written to the stdout. As an example, please take a look at ./data/trans.sh
(please, execute sbt assembly to create the project jar before using trans.sh)
