class Card
{
    final suit
    final value
    final name
    final isAce

    Card(Suit suit, int value, String name)
    {
        this(suit, value, name, false)
    }

    Card(Suit suit, int value, String name, boolean ace)
    {
        assert suit != null
        assert value > 0 && value < 12
        assert name != null
        assert name != ""

        this.suit = suit
        this.value = value
        this.name = name
        this.isAce = ace
    }

    String toString()
    {
        return name + " of " + suit
    }
}
