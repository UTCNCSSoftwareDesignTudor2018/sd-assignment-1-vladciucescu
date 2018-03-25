package business;

import dataAccess.DataAccessException;
import dataAccess.ProfileDAO;
import entity.Profile;

public class ProfileService {

    public Profile updateName(Profile profile, String newName) throws ProfileException{
        ProfileDAO profileDAO = new ProfileDAO();
        Profile updatedProfile = null;
        if (profile.getName().equals(newName)) {
            throw new ProfileException("New name must be different from old name");
        }

        updatedProfile = new Profile(profile.getId(), newName, profile.getId_card_number(), profile.getAddress());
        try {
            profileDAO.insert(updatedProfile);
        } catch (DataAccessException e) {
            throw new ProfileException("Error updating profile");
        }

        return updatedProfile;
    }

    public Profile updateAddress(Profile profile, String newAddress) throws ProfileException{
        ProfileDAO profileDAO = new ProfileDAO();
        Profile updatedProfile = null;
        if (profile.getAddress().equals(newAddress)) {
            throw new ProfileException("New address must be different from old address");
        }

        updatedProfile = new Profile(profile.getId(), profile.getName(), profile.getId_card_number(), newAddress);
        try {
            profileDAO.insert(updatedProfile);
        } catch (DataAccessException e) {
            throw new ProfileException("Error updating profile");
        }

        return updatedProfile;
    }

}
