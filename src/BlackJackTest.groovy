/**
 * Created by hieplt on 9/19/2014.
 */

Game game = new Game();

def cards =[ new Card(Suit.clubs, 3, "Three"),
             new Card(Suit.clubs, 7, "Nine"),
             new Card(Suit.diamonds, 11, "Ace", true),
];

def point = game.calculateValue(cards);

assert point==21:"Error with 3-7-Ace";

cards =[ new Card(Suit.clubs, 3, "Three"),
         new Card(Suit.clubs, 3, "Three"),
         new Card(Suit.clubs, 3, "Three"),
         new Card(Suit.diamonds, 11, "Ace", true),
];

point = game.calculateValue(cards);

assert point==10 : "Error with 4th Ace";