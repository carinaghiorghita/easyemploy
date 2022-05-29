import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

declare var apiRTC: any;

@Component({
  selector: 'app-video-session',
  templateUrl: './video-session.component.html',
  styleUrls: ['./video-session.component.css']
})
export class VideoSessionComponent implements OnInit {

  conversationId: string | null = '';

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.conversationId = this.route.snapshot.paramMap.get('id');

    this.getOrcreateConversation();
  }

  getOrcreateConversation() {
    var localStream: any = null;

    // create user agent
    var ua = new apiRTC.UserAgent({
      uri: 'apzkey:myDemoApiKey'
    });

    // register
    ua.register().then((session: any) => {

      // get conversation
      const conversation = session.getConversation(this.conversationId);

      // event listener: new stream joins conversation
      conversation.on('streamListChanged', (streamInfo: any) => {
        console.log("streamListChanged :", streamInfo);
        if (streamInfo.listEventType === 'added') {
          if (streamInfo.isRemote === true) {
            conversation.subscribeToMedia(streamInfo.streamId)
              .then(() => {
                console.log('subscribeToMedia success');
              }).catch((err: any) => {
              console.error('subscribeToMedia error', err);
            });
          }
        }
      });

      // event listener: stream is added to/removed from conversation
      conversation.on('streamAdded', (stream: any) => {
        stream.addInDiv('remote-container', 'remote-media-' + stream.streamId, {}, false);
      }).on('streamRemoved', (stream: any) => {
        stream.removeFromDiv('remote-container', 'remote-media-' + stream.streamId);
      });

      // create local stream
      ua.createStream({
        constraints: {
          audio: true,
          video: true
        }
      })
        .then((stream: any) => {

          console.log('createStream :', stream);

          // save local stream
          localStream = stream;
          stream.removeFromDiv('local-container', 'local-media');
          stream.addInDiv('local-container', 'local-media', {}, true);

          // join conversation
          conversation.join()
            .then((response: any) => {
              // publish local stream
              conversation.publish(localStream);
            }).catch((err: any) => {
            console.error('Conversation join error', err);
          });

        }).catch((err: any) => {
        console.error('create stream error', err);
      });
    });  }
}
