# Grow Less Grass
Minecraft mod to grow less grass.

Currently listens to bonemeal events and will grow tall or short grass based on the configuration chances.

```toml
#Chance for short grass generation (0 = none, 1 = always)
#Range: 0.0 ~ 1.0
bonemeal_short_grass_chance = 0.25
#Chance for tall grass generation (0 = none, 1 = always)
#Range: 0.0 ~ 1.0
bonemeal_tall_grass_chance = 0.0
```

# Why?
Because I don't like Minecraft tall grass.  Some short grass is OK.

I also open the [Sapixcraft](https://sapixcraft.com/) resource pack and change the creeper hue to blue so I can see it.

# Credits
Thanks to Serilum's mod [Random Bone Meal Flowers](https://github.com/Serilum/Random-Bone-Meal-Flowers) for the main logic on how to replace blocks from a bonemeal event.  I copied most of their `onBonemeal` event.

I also made a [No Tall Grass](https://github.com/FesterHead/No-Tall-Grass) data pack that stops tall grass on world creation.  See also [No More Grass and Kelp](https://modrinth.com/datapack/no-more-grass-and-kelp) data pack that prevents more than just tall grass.
