package business;

import dataAccess.AdminDAO;
import dataAccess.DataAccessException;
import dataAccess.ProfileDAO;
import dataAccess.StudentDAO;
import dataAccess.entity.AdminProfile;
import dataAccess.entity.Profile;
import dataAccess.entity.StudentProfile;

public class ProfileService {

    public AdminProfile getAdmin(int id) throws ProfileException {
        AdminDAO adminDAO = new AdminDAO();
        AdminProfile profile;
        try {
            profile = adminDAO.findById(id);
        } catch (DataAccessException e) {
            throw new ProfileException("Unable to load profile");
        }
        return profile;
    }

    public StudentProfile getStudent(int id) throws ProfileException {
        StudentDAO studentDAO = new StudentDAO();
        StudentProfile profile;
        try {
            profile = studentDAO.findById(id);
        } catch (DataAccessException e) {
            throw new ProfileException("Unable to load profile");
        }
        return profile;
    }

    public Profile updateName(Profile profile, String newName) throws ProfileException {
        ProfileDAO profileDAO = new ProfileDAO();
        Profile updatedProfile;
        if (profile.getName().equals(newName)) {
            throw new ProfileException("New name must be different from old name");
        }

        updatedProfile = new Profile(profile.getId(), newName, profile.getId_card_number(), profile.getAddress());
        try {
            profileDAO.update(updatedProfile);
        } catch (DataAccessException e) {
            throw new ProfileException("Error updating profile");
        }

        return updatedProfile;
    }

    public Profile updateAddress(Profile profile, String newAddress) throws ProfileException {
        ProfileDAO profileDAO = new ProfileDAO();
        Profile updatedProfile;
        if (profile.getAddress().equals(newAddress)) {
            throw new ProfileException("New address must be different from old address");
        }

        updatedProfile = new Profile(profile.getId(), profile.getName(), profile.getId_card_number(), newAddress);
        try {
            profileDAO.update(updatedProfile);
        } catch (DataAccessException e) {
            throw new ProfileException("Error updating profile");
        }

        return updatedProfile;
    }

}
