export interface Comment {
    id: number;
    topicId: number;
    userId: number;
    content: string;
    time: Date;
    status: boolean;
  }