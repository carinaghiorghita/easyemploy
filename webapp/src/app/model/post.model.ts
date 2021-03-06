import {Company} from "./company.model";
import {User} from "./user.model";

export class Post {
  id: number = 0;
  jobTitle: string = "";
  experienceLevel: string = "";
  salary: number | undefined;
  description: string = "";
  dateCreated: string = "";
  dateLastEdited: string = "";
  company: Company = new Company();
  applicants: number=0;
}
