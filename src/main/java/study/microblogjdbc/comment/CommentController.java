package study.microblogjdbc.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public String createComment(@PathVariable(name = "postId") Long postId,
                                @RequestParam String content) {
        commentService.createComment(postId, content);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable(name = "postId") Long postId,
                                @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }
}