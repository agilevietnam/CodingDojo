/**
 * Created by hieplt on 9/19/2014.
 */
class Game {
    // The master deck. We copy this array into the one we're using for the deal
    final ArrayList<Card> DECK = [new Card(Suit.clubs, 2, "Two"),
    new Card(Suit.clubs, 3, "Three"),
    new Card(Suit.clubs, 4, "Four"),
    new Card(Suit.clubs, 5, "Five"),
    new Card(Suit.clubs, 6, "Six"),
    new Card(Suit.clubs, 7, "Seven"),
    new Card(Suit.clubs, 8, "Eight"),
    new Card(Suit.clubs, 9, "Nine"),
    new Card(Suit.clubs, 10, "Ten"),
    new Card(Suit.clubs, 10, "Jack"),
    new Card(Suit.clubs, 10, "Queen"),
    new Card(Suit.clubs, 10, "King"),
    new Card(Suit.clubs, 11, "Ace", true),
    new Card(Suit.diamonds, 2, "Two"),
    new Card(Suit.diamonds, 3, "Three"),
    new Card(Suit.diamonds, 4, "Four"),
    new Card(Suit.diamonds, 5, "Five"),
    new Card(Suit.diamonds, 6, "Six"),
    new Card(Suit.diamonds, 7, "Seven"),
    new Card(Suit.diamonds, 8, "Eight"),
    new Card(Suit.diamonds, 9, "Nine"),
    new Card(Suit.diamonds, 10, "Ten"),
    new Card(Suit.diamonds, 10, "Jack"),
    new Card(Suit.diamonds, 10, "Queen"),
    new Card(Suit.diamonds, 10, "King"),
    new Card(Suit.diamonds, 11, "Ace", true),
    new Card(Suit.hearts, 2, "Two"),
    new Card(Suit.hearts, 3, "Three"),
    new Card(Suit.hearts, 4, "Four"),
    new Card(Suit.hearts, 5, "Five"),
    new Card(Suit.hearts, 6, "Six"),
    new Card(Suit.hearts, 7, "Seven"),
    new Card(Suit.hearts, 8, "Eight"),
    new Card(Suit.hearts, 9, "Nine"),
    new Card(Suit.hearts, 10, "Ten"),
    new Card(Suit.hearts, 10, "Jack"),
    new Card(Suit.hearts, 10, "Queen"),
    new Card(Suit.hearts, 10, "King"),
    new Card(Suit.hearts, 11, "Ace", true),
    new Card(Suit.spades, 2, "Two"),
    new Card(Suit.spades, 3, "Three"),
    new Card(Suit.spades, 4, "Four"),
    new Card(Suit.spades, 5, "Five"),
    new Card(Suit.spades, 6, "Six"),
    new Card(Suit.spades, 7, "Seven"),
    new Card(Suit.spades, 8, "Eight"),
    new Card(Suit.spades, 9, "Nine"),
    new Card(Suit.spades, 10, "Ten"),
    new Card(Suit.spades, 10, "Jack"),
    new Card(Suit.spades, 10, "Queen"),
    new Card(Suit.spades, 10, "King"),
    new Card(Suit.spades, 11, "Ace", true)]

/*
 * This method calculates the points value of a hand. It handles Ace
 * calculations automatically; if an Ace(s) with value 11 will put the user
 * over 21, then it auto-adjusts the value(s) down to 1.
 */
    static def calculateValue(List<Card> cards)
    {
        def value = 0
        def aces = 0

        for (Card c : cards)
        {
            if (c.isAce)
            {
                aces++
            }

            value += c.value

            // This corrects for the case where 11 pts would put them over 21,
            // but since it's an Ace, we'll count it as 1 pt to keep them under.
            // Works for any number of aces in the hand.
            while (value > 21 && aces > 0)
            {
                value -= 10
                aces--
            }
        }

        return value
    }

/*
 * Method to play the human side of the deal. Will prompt the user whether
 * they want to hit or stand until they finally stand or bust.
 */
    def playHuman(List player, List cards)
    {
        def playerTurn = true
        def playerPts = 0
        def playerFirstRun = true

        // player's turn
        while (playerTurn)
        {
            playerPts = calculateValue(player)

            println "Your cards: ${player.join(', ')}"

            if (playerFirstRun && playerPts == 21)
            {
                println "Blackjack! 21 points on your first deal. Congrats!"
                break
            }

            playerFirstRun = false

            if (playerPts > 21)
            {
                println "You went bust with $playerPts} points. Too bad.\n"
                break
            }

            def choice = System.console().readLine(
                    "You have $playerPts points. Would you like to (h)it or (s)tand? ")

            println "\n"
            choice = choice.toLowerCase()


            switch(choice)
            {
                case "h":
                case "hit":
                    player << cards.remove(0)
                    break

                case "s":
                case "stand":
                    playerTurn = false
                    break

                default:
                    println "Invalid option: ${choice}. Type 'h' to hit; 's' to stand."
            }
        }

        def outcome = (playerPts > 21) ? "busts" : "stands"
        println "Player ${outcome} with ${playerPts} points.\n"

        return [playerPts, playerFirstRun]
    }

/*
 * Auto-plays the dealer's hand. Since the dealer has fixed rules, this
 * method requires no user interaction.
 */
    def playDealer(List dealer, List cards)
    {
        def dealerTurn = true
        def dealerPts = 0
        def dealerFirstRun = true

        // dealer's turn; it's all automated
        while (dealerTurn)
        {
            dealerPts = calculateValue(dealer)

            println "Dealer cards: ${dealer.join(', ')}"

            if (dealerFirstRun && dealerPts == 21)
            {
                println "Blackjack! 21 points on the first deal. Go me!"
                break
            }

            dealerFirstRun = false

            if (dealerPts > 21)
            {
                println "Dealer went bust with ${dealerPts} points. Oh well.\n"
                break
            }

            switch(dealerPts)
            {
                case 1..16:
                    println("Dealer has ${dealerPts} points and must hit.\n")
                    dealer << cards.remove(0)
                    break

                case 17..21:
                    println("Dealer has ${dealerPts} points and must stand.\n")
                    dealerTurn = false
                    break
            }
        }

        def outcome = (dealerPts > 21) ? "busts" : "stands"
        println "Dealer ${outcome} with ${dealerPts} points.\n"

        return [dealerPts, dealerFirstRun]
    }

/*
 * Displays the results of the game, depending on who won. Briefly:
 *  - If both players bust, dealer wins
 *  - If a player gets 21 on the initial deal, they win, unless both
 *    get 21 on first deal, in which case it's a tie
 *  - If players score the same number of points, it's a tie
 *  - The player who scores the highest without going over 21 wins
 */
    static def displayResults(playerPts, playerFirstRun, dealerPts, dealerFirstRun)
    {
        // Both bust
        if (playerPts > 21 && dealerPts > 21)
        {
            println "Both players bust; dealer wins. Them\'s the rules"
        }
        // Player busts
        else if (playerPts > 21)
        {
            println "Player busts; dealer wins with ${dealerPts} points. Nice try."
        }
        // Dealer busts
        else if (dealerPts > 21)
        {
            println "Dealer busts; player wins with ${playerPts} points. Wicked."
        }
        // Player gets 21 on first deal
        else if (playerFirstRun && !dealerFirstRun)
        {
            println "Player wins with ${playerPts} points on initial deal. Sweet!"
        }
        // Dealer gets 21 on first deal
        else if (dealerFirstRun && !playerFirstRun)
        {
            println "Dealer wins with ${dealerPts} points on initial deal. Boo-ya!"
        }
        // Tie game (also handles case where they tie because of 21 on first deal)
        else if (playerPts == dealerPts)
        {
            println "It's a tie, with ${playerPts} points each."
        }
        // Player wins w/o busts
        else if (playerPts > dealerPts)
        {
            println "Player wins with ${playerPts} points. Well done."
        }
        // Dealer wins w/o busts
        else if (dealerPts > playerPts)
        {
            println "Dealer wins with ${dealerPts} points. In your face, player!"
        }
    }

    def play()
    {
        // shuffle the deck
        def cards = new ArrayList<Card>(DECK)
        Collections.shuffle(cards)

        def dealer =[]
        def player = []

        // deal the cards
        player << cards.remove(0)
        dealer << cards.remove(0)
        player << cards.remove(0)
        dealer << cards.remove(0)

        // this is a nifty reason to use Groovy
        def (int playerPts, boolean playerFirstRun) = playHuman(player, cards)
        def (int dealerPts, boolean dealerFirstRun) = playDealer(dealer, cards)

        displayResults(playerPts, playerFirstRun, dealerPts, dealerFirstRun)
    }
}
