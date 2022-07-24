package isobar.panasonic.entity.data;

public enum LocationStore {
    HOCHIMINH, HANOI;

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        switch (this) {
            case HOCHIMINH:
                return "TP. Hồ Chí Minh ";
            case HANOI:
                return "Hà Nội ";
            default:
                return "";
        }
    }
}