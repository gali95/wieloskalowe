package automatKomorkowy.DwoDimension.ziarna;

public interface GetMostOftenNeighbourRule {

    WholeZiarnoInfo SelectNeighbour(ZiarnoCounter[] neighData, int neighDataCount);

}
