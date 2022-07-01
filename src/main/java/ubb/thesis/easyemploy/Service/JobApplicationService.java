package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.JobApplication;
import ubb.thesis.easyemploy.Domain.Entities.JobApplicationKey;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Repository.JobApplicationRepository;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobApplicationService {

    @Autowired
    private final JobApplicationRepository jobApplicationRepository;

    public void save(JobApplication jobApplication) {
        jobApplicationRepository.save(jobApplication);
    }

    @Transactional
    public void update(JobApplication jobApplication) {
        jobApplicationRepository.findById(jobApplication.getId())
                .ifPresent(p -> {
                    p.setCL(jobApplication.getCL());
                    p.setCV(jobApplication.getCV());
                    p.setAddress(jobApplication.getAddress());
                    p.setDob(jobApplication.getDob());
                    p.setEmail(jobApplication.getEmail());
                    p.setFirstName(jobApplication.getFirstName());
                    p.setLastName(jobApplication.getLastName());
                    p.setPhone(jobApplication.getPhone());
                    p.setSalutations(jobApplication.getSalutations());
                    p.setFeedback(jobApplication.getFeedback());
                    p.setInterviewTime(jobApplication.getInterviewTime());
                    p.setInterviewLink(jobApplication.getInterviewLink());
                    p.setDateCreated(jobApplication.getDateCreated());
                    p.setDateLastEdited(jobApplication.getDateLastEdited());
                });
    }

    public byte[] getExcel(Post post) throws IOException {
        try {
            var applications = jobApplicationRepository.findAllByPost(post);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(post.getJobTitle());
            // set width for bigger columns
            sheet.setColumnWidth(2, 15 * 256);
            sheet.setColumnWidth(3, 15 * 256);
            sheet.setColumnWidth(4, 15 * 256);
            sheet.setColumnWidth(5, 30 * 256);
            sheet.setColumnWidth(6, 15 * 256);
            sheet.setColumnWidth(7, 30 * 256);
            sheet.setColumnWidth(8, 25 * 256);
            sheet.setColumnWidth(9, 25 * 256);
            sheet.setColumnWidth(10, 25 * 256);
            sheet.setColumnWidth(11, 25 * 256);
            sheet.setColumnWidth(12, 30 * 256);

            // create header row
            XSSFRow header = sheet.createRow(0);

            header.createCell(0).setCellValue("Nr.");
            header.createCell(1).setCellValue("Salutation");
            header.createCell(2).setCellValue("First Name");
            header.createCell(3).setCellValue("Last Name");
            header.createCell(4).setCellValue("Date of Birth");
            header.createCell(5).setCellValue("Email");
            header.createCell(6).setCellValue("Phone Number");
            header.createCell(7).setCellValue("Address");
            header.createCell(8).setCellValue("Date applied");
            header.createCell(9).setCellValue("Date last edited");
            header.createCell(10).setCellValue("Feedback");
            header.createCell(11).setCellValue("Interview Time");
            header.createCell(12).setCellValue("Interview Link");

            int rowCount = 1;

            for (JobApplication application : applications) {
                XSSFRow row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(rowCount - 1);
                row.createCell(1).setCellValue(application.getSalutations());
                row.createCell(2).setCellValue(application.getFirstName());
                row.createCell(3).setCellValue(application.getLastName());
                row.createCell(4).setCellValue(application.getDob().toString());
                row.createCell(5).setCellValue(application.getEmail());
                row.createCell(6).setCellValue(application.getPhone());
                row.createCell(7).setCellValue(application.getAddress());
                row.createCell(8).setCellValue(application.getDateCreated().format(formatter));
                row.createCell(9).setCellValue(application.getDateLastEdited().format(formatter));
                row.createCell(10).setCellValue(application.getFeedback());
                if (application.getInterviewTime() == null)
                    row.createCell(11).setCellValue("");
                else
                    row.createCell(11).setCellValue(application.getInterviewTime().format(formatter));
                row.createCell(12).setCellValue(application.getInterviewLink());
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                workbook.write(bos);
            } finally {
                bos.close();
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayOutputStream().toByteArray();
    }

    public List<User> getApplicantsForPost(Post post) {
        return jobApplicationRepository.findAll()
                .stream()
                .filter(jobApplication -> jobApplication.getPost().getId().equals(post.getId()))
                .map(JobApplication::getUser)
                .collect(Collectors.toList());
    }

    public List<Post> getPostsForApplicant(User user) {
        return jobApplicationRepository.findAll()
                .stream()
                .filter(jobApplication -> jobApplication.getUser().getId().equals(user.getId()))
                .map(JobApplication::getPost)
                .collect(Collectors.toList());
    }

    public boolean exists(JobApplicationKey jobApplicationKey) {
        return jobApplicationRepository.existsById(jobApplicationKey);
    }

    @Transactional
    public JobApplication getByIdNoFiles(JobApplicationKey id) {
        var application = jobApplicationRepository.getOne(id);
        application.setCL(null);
        application.setCV(null);
        return application;
    }

    @Transactional
    public List<JobApplication> getApplicationsWithInterviewsForUser(Long userId) {
        List<JobApplication> applications = jobApplicationRepository.findAll()
                .stream()
                .filter(jobApplication -> jobApplication.getUser().getId().equals(userId) && !jobApplication.getInterviewLink().equals(""))
                .collect(Collectors.toList());

        applications.forEach(jobApplication ->
        {
            jobApplication.setCV(null);
            jobApplication.setCL(null);
        });

        return applications;
    }

    @Transactional
    public List<JobApplication> getApplicationsForPost(Post post) {
        var applications = jobApplicationRepository.findAll()
                .stream()
                .filter(jobApplication -> jobApplication.getPost().equals(post))
                .collect(Collectors.toList());

        applications.forEach(jobApplication -> {
            jobApplication.setCV(null);
            jobApplication.setCL(null);
        });

        return applications;
    }

    public void deleteById(JobApplicationKey key) {
        jobApplicationRepository.deleteById(key);
    }
}
