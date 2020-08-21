# Polygons

A project for the purpose of creating polygon datastructures for potential applications to modelling.

## Bordering Area

Imagine a tribe of people living on some landmass. These people will naturally try to choose the most efficient food source. In terms of fishing, we can, naively, say that the more area of sea beside their coastline the more productive their fishing will be overall. 

To this end, we want a way to calculate some notion of this area. Let's suppose that the landmass is a polygon. We can list certain edges to this polygon as edges bordering water. Then we can calculate the area of water some constant distance out from these edges. This is, in essence, the primary functionality of the polygon structure as it is. 

## Polygonal Area

Polygonal area is computed by dividing the polygon into triangles, connected at the centroid. There is an issue, however: this method only works if the centroid lies within the polygon; this is not always the case. We need an algorithm which gives the area of the polygon without relying on the centroid; so long as there can be found some interior point to the polygon, this is possible.

