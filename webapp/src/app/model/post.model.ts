import {Company} from "./company.model";

export class Post {
  id: number = 0;
  jobTitle: string = "";
  experienceLevel: string = "";
  salary: number = 0;
  description: string = "";
  dateCreated: string = "";
  company: Company = new Company();
}
